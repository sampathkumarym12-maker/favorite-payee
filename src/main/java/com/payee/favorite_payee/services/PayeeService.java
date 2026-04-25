package com.payee.favorite_payee.services;

import java.util.List;

import com.payee.favorite_payee.dto.PayeeRequestDTO;
import com.payee.favorite_payee.dto.PayeeResponseDTO;

public interface PayeeService {

    PayeeResponseDTO createPayee(PayeeRequestDTO request);

    PayeeResponseDTO updatePayee(Long id, PayeeRequestDTO request);

    void deletePayee(Long id);

    List<PayeeResponseDTO> getPayeesPaginated(Long customerId, Integer pageNumber, Integer pageSize, Boolean isFavorite);

    PayeeResponseDTO getPayeeById(Long id);
}