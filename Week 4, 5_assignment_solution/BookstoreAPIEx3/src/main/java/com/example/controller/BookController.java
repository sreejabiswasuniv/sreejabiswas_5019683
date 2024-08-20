package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	// GET /books/filter - Filter books based on title and author
	@GetMapping("/filter")
	public List<Book> filterBooks(@RequestParam(required = false) String title,
			@RequestParam(required = false) String author) {

		if (title != null && author != null) {
			return bookRepository.findByTitleAndAuthor(title, author);
		} else if (title != null) {
			return bookRepository.findByTitle(title);
		} else if (author != null) {
			return bookRepository.findByAuthor(author);
		} else {
			return bookRepository.findAll();
		}
	}

	// GET /books - Retrieve all books
	@GetMapping
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	// GET /books/{id} - Retrieve a book by ID
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		Optional<Book> book = bookRepository.findById(id);
		return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// POST /books - Create a new book
	@PostMapping
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		Book savedBook = bookRepository.save(book);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
	}

	// PUT /books/{id} - Update an existing book
	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
		if (!bookRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		book.setId(id);
		Book updatedBook = bookRepository.save(book);
		return ResponseEntity.ok(updatedBook);
	}

	// DELETE /books/{id} - Delete a book by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		if (!bookRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		bookRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
