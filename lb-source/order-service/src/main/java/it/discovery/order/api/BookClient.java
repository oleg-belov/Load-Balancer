package it.discovery.order.api;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class BookClient {

	@Autowired
	private RestTemplate restTemplate;

	public double getPrice(int bookId) {
		Map<String, Object> result = this.restTemplate.getForObject("/" + bookId, Map.class);

		return NumberUtils.toDouble(result.get("price").toString());
	}
}
