package com.payee.favorite_payee.repository;

import com.payee.favorite_payee.models.PayeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayeeRepository extends JpaRepository<PayeeModel, Long> {
}
