package com.obelov.balancer.healthcheck;

import com.obelov.balancer.config.LoadBalancerConfiguration;
import com.obelov.balancer.config.ServerDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ActuatorHealthCheckService implements HealthCheckService {

	private final Map<String, Boolean> servers;

	public ActuatorHealthCheckService(LoadBalancerConfiguration loadBalancerConfiguration) {
		servers = loadBalancerConfiguration.getServers()
				.stream().filter(ServerDefinition::isEnabled)
				.collect(Collectors.toConcurrentMap(
						server -> server.getUrl(),
						server -> true));
	}

	@Override
	public List<String> getAvailableServers() {
		return servers.entrySet().stream()
				.filter(entry -> entry.getValue())
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}

	@Scheduled(fixedDelay = 20000)
	private void checkServersAvailability() {
		servers.entrySet().parallelStream().forEach(entry -> {
			servers.put(entry.getKey(),
					pingServerAndSaveResult(entry.getKey()));
		});
	}

	private boolean pingServerAndSaveResult(String url) {
		RestTemplate restTemplate = new RestTemplate();
		try {
			log.info("Pinging server ... " + url);
			Map<String, Object> status = restTemplate.getForObject(url +
					"/actuator/health", Map.class);
			return "UP".equals(status.get("status"));
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return false;
		}
	}
}