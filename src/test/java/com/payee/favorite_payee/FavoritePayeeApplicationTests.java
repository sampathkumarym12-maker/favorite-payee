package com.payee.favorite_payee;

import com.payee.favorite_payee.dto.PayeeRequestDTO;
import com.payee.favorite_payee.dto.PayeeResponseDTO;
import com.payee.favorite_payee.models.BankCodeMapping;
import com.payee.favorite_payee.models.PayeeModel;
import com.payee.favorite_payee.repository.BankCodeMappingRepository;
import com.payee.favorite_payee.repository.PayeeRepository;
import com.payee.favorite_payee.services.CustomerService;
import com.payee.favorite_payee.services.impl.PayeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayeeServiceTest {

	@Mock
	private PayeeRepository payeeRepository;

	@Mock
	private CustomerService customerService;

	@Mock
	private BankCodeMappingRepository bankCodeMappingRepository;

	@InjectMocks
	private PayeeServiceImpl payeeService;

	private PayeeModel samplePayee;

	@BeforeEach
	void setUp() {
		samplePayee = new PayeeModel();
		samplePayee.setId(1L);
		samplePayee.setAccountName("John Doe");
		samplePayee.setIban("AA00BNK1123456");
	}

	@Test
	void getPayeeById_Success() {
		when(payeeRepository.findById(1L)).thenReturn(Optional.of(samplePayee));
		PayeeResponseDTO result = payeeService.getPayeeById(1L);
		assertNotNull(result);
		assertEquals("John Doe", result.getAccountName());
		verify(payeeRepository, times(1)).findById(1L);
	}

	@Test
	void getPayeeById_NotFound() {
		when(payeeRepository.findById(2L)).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, () -> payeeService.getPayeeById(2L));
		verify(payeeRepository, times(1)).findById(2L);
	}

	@Test
	void addPayee_Success() {
		PayeeRequestDTO request = new PayeeRequestDTO();
		request.setAccountName("Jane Doe");
		request.setIban("BB00BNK1123456");

		PayeeModel savedPayee = new PayeeModel();
		savedPayee.setId(2L);
		savedPayee.setAccountName("Jane Doe");
		savedPayee.setIban("BB00BNK1123456");

		BankCodeMapping mockBank = new BankCodeMapping();
		mockBank.setBankName("Test Bank");
		mockBank.setCode("BNK1");

		when(bankCodeMappingRepository.findByCode("BNK1")).thenReturn(Optional.of(mockBank));

		when(payeeRepository.save(any(PayeeModel.class))).thenReturn(savedPayee);

		PayeeResponseDTO result = payeeService.createPayee(request);

		assertNotNull(result);
		assertEquals("Jane Doe", result.getAccountName());
		verify(payeeRepository, times(1)).save(any(PayeeModel.class));
	}

	@Test
	void updatePayee_Success() {
		PayeeRequestDTO request = new PayeeRequestDTO();
		request.setAccountName("John Smith");
		request.setIban("CC00BNK1123456");

		BankCodeMapping mockBank = new BankCodeMapping();
		mockBank.setBankName("Test Bank");
		mockBank.setCode("BNK1");

		when(bankCodeMappingRepository.findByCode("BNK1")).thenReturn(Optional.of(mockBank));

		when(payeeRepository.findById(1L)).thenReturn(Optional.of(samplePayee));

		when(payeeRepository.save(any(PayeeModel.class))).thenAnswer(i -> i.getArguments()[0]);

		PayeeResponseDTO result = payeeService.updatePayee(1L, request);

		assertNotNull(result);
		assertEquals("John Smith", result.getAccountName());

		verify(payeeRepository).findById(1L);
		verify(payeeRepository).save(any(PayeeModel.class));
	}

	@Test
	void updatePayee_NotFound() {
		PayeeRequestDTO request = new PayeeRequestDTO();
		request.setAccountName("John Smith");
		request.setIban("CC00BNK1123456");

		when(payeeRepository.findById(2L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> payeeService.updatePayee(2L, request));
		verify(payeeRepository, times(1)).findById(2L);
		verify(payeeRepository, never()).save(any(PayeeModel.class));
	}


	@Test
	void getAllPayees_Success() {
		PayeeModel payee2 = new PayeeModel();
		payee2.setId(2L);
		payee2.setAccountName("Jane Doe");
		payee2.setIban("BB00BNK1123456");
		payee2.setIsFavorite(false);

		List<PayeeModel> payeeList = Arrays.asList(samplePayee, payee2);

		Page<PayeeModel> payeePage = new PageImpl<>(payeeList);

		when(payeeRepository.findByCustomerModel_IdAndIsDeletedFalse(eq(1L), any(Pageable.class)))
				.thenReturn(payeePage);

		List<PayeeResponseDTO> result = payeeService.getPayeesPaginated(1L, null, null, null);

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("Jane Doe", result.get(1).getAccountName());

//		verify(payeeRepository, times(1)).findByCustomerModel_IdAndIsDeletedFalse(eq(1L), any(Pageable.class));
	}
}