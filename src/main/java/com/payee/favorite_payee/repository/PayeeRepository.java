package com.payee.favorite_payee.repository;

import com.payee.favorite_payee.models.PayeeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayeeRepository extends JpaRepository<PayeeModel, Long> {

//    List<PayeeModel> findByCustomerModelIdAndIsDeletedFalse(Long customerId);

    Page<PayeeModel> findByCustomerModel_IdAndIsDeletedFalse(Long customerId, Pageable pageable);

    Page<PayeeModel> findByCustomerModel_IdAndIsDeletedFalseAndIsFavorite(Long customerId, Boolean isFavorite, Pageable pageable);
}
