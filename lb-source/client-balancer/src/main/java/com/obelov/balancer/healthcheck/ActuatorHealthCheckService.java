package com.obelov.balancer.healthcheck;

import com.obelov.balancer.config.LoadBalancerConfiguration;
import com.obelov.balancer.config.ServerDefinition;
import com.obelov.balancer.rest.service.RestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ActuatorHealthCheckService implements HealthCheckService {

	private final Map<String, Boolean> servers;

	private final RestService restService;

	public ActuatorHealthCheckService(RestService restService, LoadBalancerConfiguration loadBalancerConfiguration) {
		this.restService = restService;
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
		try {
			log.info("Pinging server ... " + url);
			Map<String, Object> status = restService.getForObject(url +
					"/actuator/health", Map.class);
			return "UP".equals(status.get("status"));
		} catch (Exception ex) {
			log.debug(ex.getMessage(), ex);
			return false;
		}
	}
}