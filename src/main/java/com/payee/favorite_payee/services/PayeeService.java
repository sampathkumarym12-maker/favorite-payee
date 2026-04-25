package com.payee.favorite_payee.services;

import java.util.List;

import com.payee.favorite_payee.dto.PayeeRequestDTO;
import com.payee.favorite_payee.dto.PayeeResponseDTO;

public interface PayeeService {

    PayeeResponseDTO createPayee(PayeeRequestDTO request);

    PayeeResponseDTO updatePayee(Long id, PayeeRequestDTO request);

    void deletePayee(Long id);

    List<PayeeResponseDTO> getAllPayees(Long customerId);

    PayeeResponseDTO toggleFavorite(Long id, Boolean isFavorite);
}