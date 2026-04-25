package com.payee.favorite_payee.services.impl;

import com.payee.favorite_payee.models.PayeeModel;
import com.payee.favorite_payee.repository.PayeeRepository;
import com.payee.favorite_payee.services.PayeeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

@Service
public class PayeeServiceImpl implements PayeeService {

	@Autowired
	PayeeRepository payeeRepository;

	@Override
	public List<PayeeModel> getAllPayees() {

		List<PayeeModel> list = payeeRepository.findAll().stream().filter(p -> Boolean.FALSE.equals(p.getIsDeleted()))
				.toList();
		
		System.out.println(list);

		if (list.isEmpty()) {
			throw new RuntimeException("No beneficiaries found");
		}

		return list;
	}
}