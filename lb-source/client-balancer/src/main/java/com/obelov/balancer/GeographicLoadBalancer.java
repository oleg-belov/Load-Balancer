package com.obelov.balancer;

import com.obelov.balancer.config.LoadBalancerConfiguration;
import com.obelov.balancer.config.ServerDefinition;
import com.obelov.balancer.healthcheck.HealthCheckService;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class GeographicLoadBalancer implements LoadBalancer {

	private final String serverZone;

	private final Map<String, ServerDefinition> servers;

	private final HealthCheckService healthCheckService;

	private Random random = new Random();

	public GeographicLoadBalancer(Environment env,
								  LoadBalancerConfiguration
										  loadBalancerConfiguration,
								  HealthCheckService healthCheckService) {
		serverZone = env.getProperty("order-service.zone",
				"Europe");

		this.healthCheckService = healthCheckService;
		servers = loadBalancerConfiguration.getServers().stream()
				.collect(Collectors.toMap(ServerDefinition::getUrl,
						server -> server));
	}
	@Override
	public String getServer() {
		List<String> zoneServers = healthCheckService.getAvailableServers()
				.stream().filter(server -> serverZone.equals(
						servers.get(server).getZone()))
				.collect(Collectors.toList());

		return zoneServers.get(random.nextInt(zoneServers.size()));
	}
}