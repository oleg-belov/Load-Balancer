package com.obelov.balancer.rest.service;

import com.obelov.balancer.config.RetryConfiguration;
import lombok.RequiredArgsConstructor;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class RestService {

	private RestTemplate restTemplate = new RestTemplate();
	private final RetryPolicy retryPolicy;

	public RestService(RetryConfiguration retryConfiguration) {
		retryPolicy = new RetryPolicy()
				.retryOn(RestClientException.class)
				.withDelay(retryConfiguration.getDelay(), TimeUnit.SECONDS)
				.withMaxRetries(retryConfiguration.getMaxRetries())
				.withMaxDuration(retryConfiguration.getMaxDuration(),
						TimeUnit.SECONDS);
	}

	public <T> T getForObject(String url, Class<T> responseType,
							  Object... uriVariables) throws RestClientException {
		return Failsafe.with(retryPolicy)
				.onFailedAttempt(ex -> System.out.println(
						"Failed attempt: " + ex.getMessage()))
				.get(() -> restTemplate.getForObject(url,
						responseType, uriVariables)
				);
	}
}
