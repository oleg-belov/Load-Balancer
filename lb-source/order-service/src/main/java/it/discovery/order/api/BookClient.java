package it.discovery.order.api;

import com.obelov.balancer.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@Slf4j
public class BookClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private LoadBalancer loadBalancer;

	public double getPrice(int bookId) {
		String url = loadBalancer.getServer();
		log.info("Book client. Using target server " + url);

		Map<String, Object> result = this.restTemplate.getForObject(
				url + "book/"+ bookId, Map.class);

		return NumberUtils.toDouble(result.get("price").toString());
	}
}
