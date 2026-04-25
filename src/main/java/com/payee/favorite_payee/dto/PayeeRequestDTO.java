package com.payee.favorite_payee.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PayeeRequestDTO {
	private String accountName;
	private String nickName;
	private String iban;
	private String bankName;
	private String customerId;
	private Boolean isFavorite;
}