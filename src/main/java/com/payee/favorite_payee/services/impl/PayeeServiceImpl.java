package com.payee.favorite_payee.services.impl;

import com.payee.favorite_payee.dto.PayeeRequestDTO;
import com.payee.favorite_payee.dto.PayeeResponseDTO;
import com.payee.favorite_payee.models.CustomerModel;
import com.payee.favorite_payee.models.PayeeModel;
import com.payee.favorite_payee.repository.PayeeRepository;
import com.payee.favorite_payee.services.PayeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayeeServiceImpl implements PayeeService {

    private final PayeeRepository payeeRepository;


    @Override
    public PayeeResponseDTO createPayee(PayeeRequestDTO request) {

        PayeeModel payee = new PayeeModel();
        payee.setAccountName(request.getAccountName());
        payee.setNickname(request.getNickName());
        payee.setIban(request.getIban());
        payee.setIsFavorite(Boolean.TRUE.equals(request.getIsFavorite()));
        payee.setIsDeleted(false);
        payee.setCustomerModel(null);

        return mapToDTO(payeeRepository.save(payee));
    }

    @Override
    public PayeeResponseDTO updatePayee(Long id, PayeeRequestDTO request) {

        PayeeModel payee = payeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payee not found"));

        payee.setAccountName(request.getAccountName());
        payee.setNickname(request.getNickName());
        payee.setIban(request.getIban());

        if (request.getIsFavorite() != null) {
            payee.setIsFavorite(request.getIsFavorite());
        }

        return mapToDTO(payeeRepository.save(payee));
    }

    @Override
    public void deletePayee(Long id) {

        PayeeModel payee = payeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payee not found"));

        payee.setIsDeleted(true);
        payeeRepository.save(payee);
    }

    @Override
    public List<PayeeResponseDTO> getAllPayees(Long customerId) {

        return payeeRepository.findByCustomerModelIdAndIsDeletedFalse(customerId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PayeeResponseDTO toggleFavorite(Long id, Boolean isFavorite) {

        PayeeModel payee = payeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payee not found"));

        payee.setIsFavorite(isFavorite);

        return mapToDTO(payeeRepository.save(payee));
    }

    private PayeeResponseDTO mapToDTO(PayeeModel payee) {
        return PayeeResponseDTO.builder()
                .id(payee.getId())
                .accountName(payee.getAccountName())
                .nickname(payee.getNickname())
                .iban(payee.getIban())
                .isFavorite(payee.getIsFavorite())
                .build();
    }
}