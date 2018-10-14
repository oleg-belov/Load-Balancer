package com.obelov.config;

import com.obelov.balancer.*;
import com.obelov.balancer.config.LoadBalancerConfiguration;
import com.obelov.balancer.config.RetryConfiguration;
import com.obelov.balancer.healthcheck.ActuatorHealthCheckService;
import com.obelov.balancer.healthcheck.HealthCheckService;
import com.obelov.balancer.rest.service.RestService;
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
	@ConfigurationProperties("retry-config")
	public RetryConfiguration retryConfiguration() {
		return new RetryConfiguration();
	}

	@Bean
	@Profile("random")
	public LoadBalancer loadBalancerHealthCheck(RestTemplateBuilder builder) {
		return new RandomLoadBalancer(healthCheckService(builder));
	}

	@Bean
	@Profile("geographic")
	public LoadBalancer loadBalancerGeographic(Environment env, RestTemplateBuilder builder) {
		return new GeographicLoadBalancer(env, this.loadBalancerConfiguration(),
				this.healthCheckService(builder));
	}

	@Bean
	@Profile("round-robin")
	public LoadBalancer loadBalancerRoundRobin(RestTemplateBuilder builder) {
		return new RoundRobinLoadBalancer(this.healthCheckService(builder));
	}

	@Bean
	@Profile("cpu-utilization")
	public LoadBalancer loadBalancerCPUUtilization(RestTemplateBuilder builder) {
		return new CPUUtilizationLoadBalancer(this.restService(builder), this.healthCheckService(builder));
	}

	@Bean
	public HealthCheckService healthCheckService(RestTemplateBuilder builder) {
		return new ActuatorHealthCheckService(
				this.restService(builder), this.loadBalancerConfiguration());
	}

	@Bean
	public RestService restService(RestTemplateBuilder builder) {
		return new RestService(this.retryConfiguration(), builder);
	}
}
