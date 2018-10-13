package it.discovery.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

	@Value("${book.service.url}")
	private String bookServiceBaseURL;

	@Bean
	public RestTemplate bookTemplate(RestTemplateBuilder builder) {
		return builder.rootUri(bookServiceBaseURL)
				.build();
	}
}
