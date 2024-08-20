package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.dto.CustomerDTO;
import com.example.entity.Customer;

@Mapper
public interface CustomerMapper {
	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

	CustomerDTO customerToCustomerDTO(Customer customer);

	Customer customerDTOToCustomer(CustomerDTO customerDTO);
}
