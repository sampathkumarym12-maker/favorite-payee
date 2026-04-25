package com.payee.favorite_payee.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long id;
    @Column(name = "c_name")
    private String name;
    @Column(name = "c_password")
    private String password;
    @OneToMany(mappedBy = "customerModel", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PayeeModel> payees;

}
