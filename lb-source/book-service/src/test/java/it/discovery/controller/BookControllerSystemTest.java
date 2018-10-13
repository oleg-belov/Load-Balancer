package it.discovery.controller;

import it.discovery.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerSystemTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void getBooks_emptyRepository_noBooksReturned() {
		List<Book> books = this.restTemplate.getForObject("/book", List.class);
		assertTrue(books.isEmpty());
	}
}