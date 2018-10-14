package it.discovery.order.config;

import com.obelov.balancer.config.RetryConfiguration;
import com.obelov.balancer.rest.service.RestService;
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
	@ConfigurationProperties("retry-config")
	public RetryConfiguration retryConfiguration() {
		return new RetryConfiguration();
	}

	@Bean
	public RestService restService() {
		return new RestService(this.retryConfiguration());
	}
}
