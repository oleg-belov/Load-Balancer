package com.obelov.balancer;

import com.obelov.balancer.healthcheck.HealthCheckService;
import com.obelov.balancer.stats.ServerStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
public class CPUUtilizationLoadBalancer implements LoadBalancer {

	private final HealthCheckService healthCheckService;

	private final Map<String, ServerStats> serverStats = new ConcurrentHashMap<>();

	@Override
	public Optional<String> getServer() {
		if (serverStats.isEmpty()) {
			return Optional.empty();
		}
		return serverStats.entrySet().stream()
				.sorted(Comparator.comparingDouble(s -> s.getValue()
						.getCpuUtilization())).findFirst()
				.map(Map.Entry::getKey);
	}

	@Scheduled(fixedDelay = 120000)
	private void queryServerStats() {
		healthCheckService.getAvailableServers()
				.parallelStream().forEach(url -> {
			serverStats.put(url,
					queryMetricsAndSaveResult(url));
		});
	}

	private ServerStats queryMetricsAndSaveResult(String url) {
		RestTemplate restTemplate = new RestTemplate();

		try {
			log.info("Pinging server ... " + url);
			Map<String, Object> status = restTemplate.getForObject(url +
					"/actuator/metrics/system.cpu.usage", Map.class);
			List<Map<String, Object>> procs =
					(List<Map<String, Object>>) status.get("measurements");
			double utilization = NumberUtils.toDouble(procs.get(0).get("value").toString());
			return new ServerStats(utilization);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			//TODO fix
			return null;
		}
	}
}
