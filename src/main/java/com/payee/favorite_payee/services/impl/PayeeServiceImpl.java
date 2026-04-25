package com.payee.favorite_payee.services.impl;

import com.payee.favorite_payee.dto.PayeeRequestDTO;
import com.payee.favorite_payee.dto.PayeeResponseDTO;
import com.payee.favorite_payee.models.BankCodeMapping;
import com.payee.favorite_payee.models.CustomerModel;
import com.payee.favorite_payee.models.PayeeModel;
import com.payee.favorite_payee.repository.BankCodeMappingRepository;
import com.payee.favorite_payee.repository.PayeeRepository;
import com.payee.favorite_payee.services.CustomerService;
import com.payee.favorite_payee.services.PayeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayeeServiceImpl implements PayeeService {

    private final PayeeRepository payeeRepository;

    private final CustomerService customerService;
    
    private final BankCodeMappingRepository bankRepo;

    @Override
    public PayeeResponseDTO createPayee(PayeeRequestDTO request) {

        String bankName = validateAndGetBankName(request.getIban());

        PayeeModel payee = new PayeeModel();
        payee.setAccountName(request.getAccountName());
        payee.setNickname(request.getNickName());
        payee.setIban(request.getIban());
        payee.setBankName(bankName); 
        payee.setIsFavorite(request.getIsFavorite());
        payee.setIsDeleted(false);
        payee.setCustomerModel(customerService.getCustomerModelById(request.getCustomerId()));

        return mapToDTO(payeeRepository.save(payee));
    }

    @Override
    public PayeeResponseDTO updatePayee(Long id, PayeeRequestDTO request) {

        PayeeModel payee = payeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payee not found"));

        payee.setAccountName(request.getAccountName());
        payee.setNickname(request.getNickName());

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
    public List<PayeeResponseDTO> getPayeesPaginated(Long customerId, Integer pageNumber, Integer pageSize, Boolean isFavorite) {

        int page = (pageNumber == null) ? 0 : pageNumber;
        int size = (pageSize == null) ? 20 : pageSize;

        Pageable pageable = PageRequest.of(page, size, Sort.by("isFavorite").descending());

        if(isFavorite != null){
            return mapToDTOList(payeeRepository.findByCustomerModel_IdAndIsDeletedFalseAndIsFavorite(customerId, isFavorite, pageable).getContent());
        }

        return mapToDTOList(payeeRepository.findByCustomerModel_IdAndIsDeletedFalse(customerId,pageable).getContent());
    }


    @Override
    public PayeeResponseDTO getPayeeById(Long id) {
         PayeeModel payeeModel = getPayeeModelById(id);
         return mapToDTO(payeeModel);
    }

    @Override
    public PayeeModel getPayeeModelById(Long id) {

        return payeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payee not found") {
                });
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

    private List<PayeeResponseDTO> mapToDTOList(List<PayeeModel> payees) {
        return payees.stream()
                .map(payee -> PayeeResponseDTO.builder()
                        .id(payee.getId())
                        .accountName(payee.getAccountName())
                        .nickname(payee.getNickname())
                        .iban(payee.getIban())
                        .isFavorite(payee.getIsFavorite())
                        .build())
                .collect(Collectors.toList());
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

        String bankCode = iban.substring(4, 8).toUpperCase().trim();

        return bankRepo.findByCode(bankCode)
                .map(BankCodeMapping::getBankName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bank code: " + bankCode));
    }
}