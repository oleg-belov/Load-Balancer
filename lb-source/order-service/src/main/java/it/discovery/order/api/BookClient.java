package it.discovery.order.api;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.obelov.balancer.LoadBalancer;
import com.obelov.balancer.config.CacheConfiguration;
import com.obelov.balancer.rest.service.RestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class BookClient {

	private final RestService restService;

	private final LoadBalancer loadBalancer;

	private final Cache<Integer, Double> cache;

	public BookClient(LoadBalancer loadBalancer, RestService restService, CacheConfiguration cacheConfiguration) {
		this.loadBalancer = loadBalancer;
		this.restService = restService;
		this.cache = Caffeine.newBuilder().initialCapacity(cacheConfiguration.getInitialCapacity())
				.maximumSize(cacheConfiguration.getMaxCapacity())
				.expireAfterWrite(cacheConfiguration.getExpirationTime(),
						TimeUnit.SECONDS).build();
	}

	public double getPrice(int bookId) {
		String url = loadBalancer.getServer().orElseThrow(() -> new RuntimeException("No available book services"));
		log.info("Book client. Using target server " + url);

		return cache.get(bookId, id -> {
			Map<String, Object> book = restService.getForObject(
					url + "book/" + bookId, Map.class);
			return NumberUtils.toDouble(book.get("price").toString());
		});
	}
}
