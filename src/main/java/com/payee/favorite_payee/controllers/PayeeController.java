package com.payee.favorite_payee.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	public PayeeResponseDTO createPayee(@RequestBody PayeeRequestDTO request) {

		return payeeService.createPayee(request);
	}

	@PutMapping("/{id}")
	public PayeeResponseDTO updatePayee(@PathVariable Long id, @RequestBody PayeeRequestDTO request) {

		return payeeService.updatePayee(id, request);
	}

	@DeleteMapping("/{id}")
	public String deletePayee(@PathVariable Long id) {
		payeeService.deletePayee(id);
		return "Payee deleted successfully";
	}

	@GetMapping
	public List<PayeeResponseDTO> getAllPayees() {
		return payeeService.getAllPayees(1L);
	}

	@PatchMapping("/{id}/favorite")
	public PayeeResponseDTO toggleFavorite(@PathVariable Long id, @RequestParam Boolean isFavorite) {

		return payeeService.toggleFavorite(id, isFavorite);
	}
}