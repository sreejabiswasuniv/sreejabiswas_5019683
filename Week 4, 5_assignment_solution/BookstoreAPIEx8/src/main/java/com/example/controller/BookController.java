package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Book;
import com.example.repository.BookRepository;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@GetMapping
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		return bookRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Book createBook(@Validated @RequestBody Book book) {
		return bookRepository.save(book);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable Long id, @Validated @RequestBody Book bookDetails) {
		return bookRepository.findById(id).map(book -> {
			book.setTitle(bookDetails.getTitle());
			book.setAuthor(bookDetails.getAuthor());
			book.setPrice(bookDetails.getPrice());
			return ResponseEntity.ok(bookRepository.save(book));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public String deleteBook(@PathVariable Long id) {
		return bookRepository.findById(id).map(book -> {
			bookRepository.delete(book);
			return "Book deleted successfully.";
		}).orElse("Book not found.");
	}

}
