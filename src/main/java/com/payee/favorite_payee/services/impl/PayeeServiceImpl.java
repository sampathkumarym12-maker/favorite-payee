package com.payee.favorite_payee.services.impl;

import com.payee.favorite_payee.dto.PayeeRequestDTO;
import com.payee.favorite_payee.dto.PayeeResponseDTO;
import com.payee.favorite_payee.models.CustomerModel;
import com.payee.favorite_payee.models.PayeeModel;
import com.payee.favorite_payee.repository.BankCodeMappingRepository;
import com.payee.favorite_payee.repository.PayeeRepository;
import com.payee.favorite_payee.services.PayeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayeeServiceImpl implements PayeeService {

    private final PayeeRepository payeeRepository;
    
    private final BankCodeMappingRepository bankRepo;

    @Override
    public PayeeResponseDTO createPayee(PayeeRequestDTO request) {

        String bankName = validateAndGetBankName(request.getIban());

        PayeeModel payee = new PayeeModel();
        payee.setAccountName(request.getAccountName());
        payee.setNickname(request.getNickname());
        payee.setIban(request.getIban());
        payee.setBankName(bankName); 
        payee.setIsFavorite(false);
        payee.setIsDeleted(false);
        payee.setCustomerModel(request.getCustomerId());

        return mapToDTO(payeeRepository.save(payee));
    }

    @Override
    public PayeeResponseDTO updatePayee(Long id, PayeeRequestDTO request) {

        PayeeModel payee = payeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payee not found"));

        payee.setAccountName(request.getAccountName());
        payee.setNickname(request.getNickname());

        if (request.getIban() != null) {
            String bankName = validateAndGetBankName(request.getIban());
            payee.setIban(request.getIban());
            payee.setBankName(bankName);
        }

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

//    @Override
//    public PayeeResponseDTO toggleFavorite(Long id, Boolean isFavorite) {
//
//        PayeeModel payee = payeeRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Payee not found"));
//
//        payee.setIsFavorite(isFavorite);
//
//        return mapToDTO(payeeRepository.save(payee));
//    }

    @Override
    public PayeeResponseDTO getPayeeById(Long id) {

         PayeeModel payeeModel = payeeRepository.findById(id)
                .orElseThrow(() -> new HttpStatusCodeException(HttpStatus.NOT_FOUND, "Payee not found") {
                });
         return mapToDTO(payeeModel);
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
    
    private String validateAndGetBankName(String iban) {

        if (iban == null || iban.isBlank()) {
            throw new IllegalArgumentException("IBAN is mandatory");
        }

        if (iban.length() > 20) {
            throw new IllegalArgumentException("IBAN max length is 20");
        }

        if (!iban.matches("[A-Za-z0-9]+")) {
            throw new IllegalArgumentException("IBAN must be alphanumeric");
        }

        if (iban.length() < 8) {
            throw new IllegalArgumentException("Invalid IBAN format");
        }

        String bankCode = iban.substring(4, 8);

        return bankRepo.findByCode(bankCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bank code in IBAN"))
                .getBankName();
    }
}