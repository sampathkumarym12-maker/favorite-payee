package com.payee.favorite_payee.controllers;

import com.payee.favorite_payee.models.PayeeModel;
import com.payee.favorite_payee.services.PayeeService;
import com.payee.favorite_payee.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayeeController {

	@Autowired
	private PayeeService payeeService;

	@GetMapping("/getAllBeneficiaries")
	public ResponseEntity<ApiResponse<List<PayeeModel>>> getAllBeneficiaries() {

		return ResponseEntity
				.ok(ApiResponse.success(payeeService.getAllPayees(), "Beneficiaries fetched successfully"));
	}
}
