package com.payee.favorite_payee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class PayeeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "p_id")
    private String id;

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
