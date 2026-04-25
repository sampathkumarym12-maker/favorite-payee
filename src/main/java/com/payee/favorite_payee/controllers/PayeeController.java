package com.payee.favorite_payee.controllers;

import com.payee.favorite_payee.services.PayeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PayeeController {

    private PayeeService payeeService;
}
