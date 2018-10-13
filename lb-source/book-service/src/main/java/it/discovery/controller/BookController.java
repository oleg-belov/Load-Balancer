package it.discovery.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.discovery.controller.exception.BookValidationException;
import it.discovery.model.Book;
import it.discovery.repository.BookRepository;

@RestController
@RequestMapping("/book")
public class BookController {

	private final BookRepository bookRepository;

	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Book> getBooks() {
		return bookRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> findBookById(@PathVariable int id, HttpServletRequest request) {
		System.out.println("Request processed by service on port: " + request.getLocalPort());

		if (id <= 0) {
			throw new BookValidationException(String.format("Book id is not valid: %s", id));
		}
		Book book = bookRepository.findById(id);
		if (book == null) {
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void saveBook(@Valid @RequestBody Book book) {
		bookRepository.save(book);
	}

	@PutMapping("/{id}")
	public void updateBook(@PathVariable int id, @Valid @RequestBody Book book) {
		book.setId(id);
		bookRepository.save(book);
	}

}
