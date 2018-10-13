package it.discovery.order.config;

import com.obelov.balancer.LoadBalancer;
import com.obelov.balancer.RandomLoadBalancer;
import com.obelov.balancer.config.LoadBalancerConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	public LoadBalancer loadBalancer(LoadBalancerConfiguration loadBalancerConfiguration) {
		return new RandomLoadBalancer(loadBalancerConfiguration);
	}
}
