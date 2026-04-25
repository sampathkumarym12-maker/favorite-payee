package com.payee.favorite_payee.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payee.favorite_payee.dto.PayeeRequestDTO;
import com.payee.favorite_payee.dto.PayeeResponseDTO;
import com.payee.favorite_payee.services.PayeeService;

@RestController
@RequestMapping("/api/payees")
public class PayeeController {

	private final PayeeService payeeService;

	public PayeeController(PayeeService payeeService) {
		this.payeeService = payeeService;
	}

	@PostMapping
	public ResponseEntity<PayeeResponseDTO> createPayee(@RequestBody PayeeRequestDTO request) {

	    PayeeResponseDTO response = payeeService.createPayee(request);

	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PayeeResponseDTO> updatePayee(@PathVariable Long id, @RequestBody PayeeRequestDTO request) {

		PayeeResponseDTO response = payeeService.updatePayee(id, request);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public String deletePayee(@PathVariable Long id) {
		payeeService.deletePayee(id);
		return "Payee deleted successfully";
	}

	@GetMapping
	public List<PayeeResponseDTO> getAllPayees(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam Boolean isFavorite) {
		return payeeService.getPayeesPaginated(1L, pageNumber, pageSize, isFavorite);
	}

	@GetMapping("/{id}")
	public PayeeResponseDTO getPayee(@PathVariable Long id){
		return  payeeService.getPayeeById(id);
	}


}