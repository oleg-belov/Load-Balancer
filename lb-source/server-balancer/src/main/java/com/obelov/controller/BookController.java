package com.obelov.controller;

import com.obelov.book.Book;
import com.obelov.rest.client.BookClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

	private final BookClient bookClient;

	@GetMapping("/{id}")
	public ResponseEntity<Book> findBookById(HttpServletRequest request, @PathVariable int id) {
		return ResponseEntity.ok(bookClient.getBook(id));
	}
}
