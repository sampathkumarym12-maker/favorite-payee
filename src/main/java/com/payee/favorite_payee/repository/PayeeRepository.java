package com.payee.favorite_payee.repository;

import com.payee.favorite_payee.models.PayeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayeeRepository extends JpaRepository<PayeeModel, Long> {

    List<PayeeModel> findByCustomerModelIdAndIsDeletedFalse(Long customerId);
}
