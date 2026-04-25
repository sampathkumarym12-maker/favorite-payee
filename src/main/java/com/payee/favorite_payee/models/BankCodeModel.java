package com.payee.favorite_payee.models;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_code")
public class BankCodeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "b_id")
    Long id;

    @Column(name = "b_code")
    String code;

    @Column(name = "b_name")
    String bankName;

}
