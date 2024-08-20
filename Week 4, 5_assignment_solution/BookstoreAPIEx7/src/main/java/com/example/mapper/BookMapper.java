package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.dto.BookDTO;
import com.example.entity.Book;

@Mapper
public interface BookMapper {
	BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

	BookDTO bookToBookDTO(Book book);

	Book bookDTOToBook(BookDTO bookDTO);
}
