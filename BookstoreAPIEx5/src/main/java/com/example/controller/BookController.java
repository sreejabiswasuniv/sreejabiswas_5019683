package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Book;
import com.example.repository.BookRepository;

@RestController
@RequestMapping("/books")
public class BookController {

	private final BookRepository bookRepository;

	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	// GET /books - Retrieve all books
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	// GET /books/{id} - Retrieve a book by ID
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		Optional<Book> book = bookRepository.findById(id);
		return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// POST /books - Create a new book with custom headers
	@PostMapping
	public ResponseEntity<Book> createBookWithHeaders(@RequestBody Book book) {
		Book savedBook = bookRepository.save(book);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Custom-Header", "HeaderValue");
		headers.add("Another-Header", "AnotherValue");

		return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(savedBook);
	}

	// PUT /books/{id} - Update an existing book with custom headers
	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBookWithHeaders(@PathVariable Long id, @RequestBody Book book) {
		if (!bookRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		book.setId(id);
		Book updatedBook = bookRepository.save(book);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Update-Status", "Book updated successfully");

		return ResponseEntity.ok().headers(headers).body(updatedBook);
	}

	// DELETE /books/{id} - Delete a book with custom headers
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBookWithHeaders(@PathVariable Long id) {
		if (!bookRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		bookRepository.deleteById(id);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Delete-Status", "Book successfully deleted");

		return ResponseEntity.noContent().headers(headers).build();
	}
}
