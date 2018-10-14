package it.discovery.order.config;

import com.obelov.balancer.GeographicLoadBalancer;
import com.obelov.balancer.LoadBalancer;
import com.obelov.balancer.RandomLoadBalancer;
import com.obelov.balancer.RoundRobinLoadBalancer;
import com.obelov.balancer.config.LoadBalancerConfiguration;
import com.obelov.balancer.healthcheck.ActuatorHealthCheckService;
import com.obelov.balancer.healthcheck.HealthCheckService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

	@Bean
	public RestTemplate bookTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	@ConfigurationProperties("book-service")
	public LoadBalancerConfiguration loadBalancerConfiguration() {
		return new LoadBalancerConfiguration();
	}

	@Bean
	@Profile("random")
	public LoadBalancer loadBalancerHealthCheck(LoadBalancerConfiguration loadBalancerConfiguration) {
		return new RandomLoadBalancer(healthCheckService());
	}

	@Bean
	@Profile("geographic")
	public LoadBalancer loadBalancerGeographic(Environment env) {
		return new GeographicLoadBalancer(env, loadBalancerConfiguration(),
				this.healthCheckService());
	}

	@Bean
	@Profile("round-robin")
	public LoadBalancer loadBalancerRoundRobin() {
		return new RoundRobinLoadBalancer(healthCheckService());
	}

	@Bean
	public HealthCheckService healthCheckService() {
		return new ActuatorHealthCheckService(this.loadBalancerConfiguration());
	}
}
