package com.payee.favorite_payee.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayeeResponseDTO {
    private Long id;
    private String accountName;
    private String nickname;
    private String iban;
    private Boolean isFavorite;
}