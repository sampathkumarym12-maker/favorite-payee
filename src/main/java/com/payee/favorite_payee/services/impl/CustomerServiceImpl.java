package com.payee.favorite_payee.services.impl;

import com.payee.favorite_payee.models.CustomerModel;
import com.payee.favorite_payee.repository.CustomerRepository;
import com.payee.favorite_payee.services.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerModel getCustomerModelById(Long id) {
        return customerRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found") {
        });
    }
}
