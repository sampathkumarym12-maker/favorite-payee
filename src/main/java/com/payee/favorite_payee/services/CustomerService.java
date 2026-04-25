package com.payee.favorite_payee.services;

import com.payee.favorite_payee.models.CustomerModel;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

    CustomerModel getCustomerModelById(Long id);
}
