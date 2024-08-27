package com.example.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
import com.example.service.MetricsService;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	private final MetricsService metricsService;

	public BookController(MetricsService metricsService) {
		this.metricsService = metricsService;
	}

	@GetMapping("/some-endpoint")
	public ResponseEntity<String> someEndpoint() {
		metricsService.incrementCustomCounter();
		return ResponseEntity.ok("Custom metric incremented");
	}

	@GetMapping
	public CollectionModel<EntityModel<Book>> getAllBooks() {
		List<EntityModel<Book>> books = bookRepository.findAll().stream()
				.map(book -> EntityModel.of(book,
						linkTo(methodOn(BookController.class).getBookById(book.getId())).withSelfRel(),
						linkTo(methodOn(BookController.class).getAllBooks()).withRel("all-books")))
				.collect(Collectors.toList());

		return CollectionModel.of(books, linkTo(methodOn(BookController.class).getAllBooks()).withSelfRel());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Book>> getBookById(@PathVariable Long id) {
		return bookRepository.findById(id)
				.map(book -> EntityModel.of(book, linkTo(methodOn(BookController.class).getBookById(id)).withSelfRel(),
						linkTo(methodOn(BookController.class).getAllBooks()).withRel("all-books")))
				.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<EntityModel<Book>> createBook(@Validated @RequestBody Book book) {
		Book savedBook = bookRepository.save(book);
		EntityModel<Book> resource = EntityModel.of(savedBook,
				linkTo(methodOn(BookController.class).getBookById(savedBook.getId())).withSelfRel(),
				linkTo(methodOn(BookController.class).getAllBooks()).withRel("all-books"));

		return ResponseEntity.created(resource.getRequiredLink("self").toUri()).body(resource);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Book>> updateBook(@PathVariable Long id,
			@Validated @RequestBody Book bookDetails) {
		return bookRepository.findById(id).map(book -> {
			book.setTitle(bookDetails.getTitle());
			book.setAuthor(bookDetails.getAuthor());
			book.setPrice(bookDetails.getPrice());
			Book updatedBook = bookRepository.save(book);

			EntityModel<Book> resource = EntityModel.of(updatedBook,
					linkTo(methodOn(BookController.class).getBookById(updatedBook.getId())).withSelfRel(),
					linkTo(methodOn(BookController.class).getAllBooks()).withRel("all-books"));

			return ResponseEntity.ok(resource);
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable Long id) {
		return bookRepository.findById(id).map(book -> {
			bookRepository.delete(book);
			return ResponseEntity.noContent().build();
		}).orElse(ResponseEntity.notFound().build());
	}
}
