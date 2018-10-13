package it.discovery.order.api;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class BookClient {

	private final RestTemplate restTemplate;

	@Value("${book.service.url}")
	private String baseUrl;

	public BookClient() {
		this.restTemplate =  new RestTemplate();
	}

	public double getPrice(int bookId) {
		Map<String, Object> result = this.restTemplate.getForObject(baseUrl + "/" + bookId, Map.class);

		return NumberUtils.toDouble(result.get("price").toString());
	}
}
