package com.obelov.rest.client;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.obelov.balancer.LoadBalancer;
import com.obelov.balancer.config.CacheConfiguration;
import com.obelov.balancer.rest.service.RestService;
import com.obelov.book.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class BookClient {

	private final RestService restService;

	private final LoadBalancer loadBalancer;

	private final Cache<Integer, Book> cache;

	public BookClient(LoadBalancer loadBalancer, RestService restService, CacheConfiguration cacheConfiguration) {
		this.loadBalancer = loadBalancer;
		this.restService = restService;
		this.cache = Caffeine.newBuilder().initialCapacity(cacheConfiguration.getInitialCapacity())
				.maximumSize(cacheConfiguration.getMaxCapacity())
				.expireAfterWrite(cacheConfiguration.getExpirationTime(),
						TimeUnit.SECONDS).build();
	}

	public Book getBook(int bookId) {
		String url = loadBalancer.getServer().orElseThrow(() -> new RuntimeException("No available book services"));
		log.info("Book client. Using target server " + url);

		return cache.get(bookId, id ->
				restService.getForObject(
						url + "book/" + bookId, Book.class)
		);
	}
}
