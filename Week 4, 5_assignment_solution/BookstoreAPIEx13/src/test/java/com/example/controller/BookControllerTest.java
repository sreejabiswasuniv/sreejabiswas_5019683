package com.example.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.entity.Book;
import com.example.repository.BookRepository;

@WebMvcTest(BookController.class)
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookRepository bookRepository;

	private List<Book> bookList;

	@BeforeEach
	void setUp() {
		Book book1 = new Book(1L, "Book Title 1", "Author 1", 0, 0);
		Book book2 = new Book(2L, "Book Title 2", "Author 2", 0, 0);

		bookList = Arrays.asList(book1, book2);

		Mockito.when(bookRepository.findAll()).thenReturn(bookList);
	}

	@Test
	public void getAllBooks_shouldReturnListOfBooks() throws Exception {
		mockMvc.perform(get("/api/books").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.length()", is(2)))
				.andExpect(jsonPath("$[0].title", is("Book Title 1")))
				.andExpect(jsonPath("$[1].title", is("Book Title 2")));
	}
}
