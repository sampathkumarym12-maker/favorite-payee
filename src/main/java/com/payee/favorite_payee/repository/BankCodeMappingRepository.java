package com.payee.favorite_payee.repository;

import com.payee.favorite_payee.models.BankCodeMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankCodeMappingRepository extends JpaRepository<BankCodeMapping, Long> {

    Optional<BankCodeMapping> findByCode(String code);
}