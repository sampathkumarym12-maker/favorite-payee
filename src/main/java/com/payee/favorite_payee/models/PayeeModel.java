package com.payee.favorite_payee.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.payee.favorite_payee.dto.PayeeResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
public class PayeeModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "p_id")
	private Long id;

	@Column(name = "p_account_name")
	private String accountName;
	@Column(name = "p_nickname")
	private String nickname;

	@Column(name = "p_iban")
	private String iban;

	@Column(name = "p_is_favorite")
	private Boolean isFavorite;

	@ManyToOne
	@JoinColumn(name = "p_customer_id")
	@JsonBackReference
	private CustomerModel customerModel;

	@Column(name = "p_is_deleted")
	private Boolean isDeleted;


//	public PayeeResponseDTO toPayeeResponseDTO(){
//		PayeeResponseDTO payeeResponseDTO = new PayeeResponseDTO();
//		payeeResponseDTO.setId(this.id);
//		payeeResponseDTO.setAccountName(this.getAccountName());
//		payeeResponseDTO.setNickname(this.getNickname());
//		payeeResponseDTO.setIban(this.getIban());
//		payeeResponseDTO.setIsFavorite(this.getIsFavorite());
//		return  payeeResponseDTO;
//	}

}
