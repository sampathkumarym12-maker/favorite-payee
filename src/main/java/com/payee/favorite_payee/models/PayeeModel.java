package com.payee.favorite_payee.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.payee.favorite_payee.models.CustomerModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "m_payee")
@ToString
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

}
