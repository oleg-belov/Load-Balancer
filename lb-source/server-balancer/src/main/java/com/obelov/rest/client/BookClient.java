package com.obelov.rest.client;

import com.obelov.balancer.LoadBalancer;
import com.obelov.balancer.rest.service.RestService;
import com.obelov.book.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class BookClient {

	private final RestService restService;

	private final LoadBalancer loadBalancer;

	public Book getBook(int bookId) {
		String url = loadBalancer.getServer().orElseThrow(() -> new RuntimeException("No available book services"));
		log.info("Book client. Using target server " + url);

		return restService.getForObject(
				url + "book/" + bookId, Book.class);
	}
}
