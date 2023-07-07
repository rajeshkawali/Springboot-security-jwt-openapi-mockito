package com.rajeshkawali.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import com.rajeshkawali.dto.CustomerDTO;
import com.rajeshkawali.service.CustomerService;


/**
 * @author Rajesh_Kawali
 *
 */
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(CustomerController.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration
@WithMockUser(username="admin",roles={"USER","ADMIN"})
public class CustomerControllerTest {

	@InjectMocks
	private CustomerController customerController;

	@MockBean
	private CustomerService customerService;
	
	@Test
	public void testGetAllCustomersReturnsListOfCustomers() {
		List<CustomerDTO> mockCustomers = new ArrayList<>();
		mockCustomers.add(CustomerDTO.builder()
				.id(1L)
				.firstName("Neil")
				.surname("Jenner")
				.mobileNumber(1234567890L)
				.smoothiePreference("Strawberry").build());
		mockCustomers.add(CustomerDTO.builder()
				.id(2L)
				.firstName("Jane")
				.surname("Smith")
				.mobileNumber(9876543210L)
				.smoothiePreference("Banana").build());
		Mockito.when(customerService.getAllCustomers()).thenReturn(mockCustomers);
		List<CustomerDTO> response = customerController.getAllCustomers();
		Assertions.assertEquals(mockCustomers.size(), response.size());
		Assertions.assertEquals(mockCustomers.get(0), response.get(0));
		Assertions.assertEquals(mockCustomers.get(1), response.get(1));
	}

	@Test
	public void testAddCustomerReturnsAddedCustomerDetails() {
		CustomerDTO customerDTO = CustomerDTO.builder()
				.firstName("Neil")
				.surname("Jenner")
				.mobileNumber(1234567890L)
				.smoothiePreference("Strawberry").build();
		CustomerDTO addedCustomer = CustomerDTO.builder()
				.id(1L)
				.firstName("Neil")
				.surname("Jenner")
				.mobileNumber(1234567890L)
				.smoothiePreference("Strawberry").build();
		Mockito.when(customerService.addCustomer(customerDTO)).thenReturn(addedCustomer);
		ResponseEntity<?> response = customerController.addCustomer(customerDTO);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assertions.assertEquals(addedCustomer, response.getBody());
	}

	@Test
	public void testCustomerByIdExistingIdReturnsCustomerDetails() {
		CustomerDTO mockCustomer = CustomerDTO.builder()
				.id(1L)
				.firstName("Rajesh")
				.surname("Kawali")
				.mobileNumber(7788665544L)
				.smoothiePreference("test").build();
		Mockito.when(customerService.customerById(1L)).thenReturn(mockCustomer);
		ResponseEntity<?> response = customerController.customerById(1L);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(mockCustomer, response.getBody());
	}

	@Test
	public void testCustomerByIdNonExistingIdThrowsException() {
		Mockito.when(customerService.customerById(anyLong())).thenReturn(null);
		Assertions.assertThrows(ResponseStatusException.class, () -> {
			customerController.customerById(1L);
		});
	}

	@Test
	public void testUpdateCustomerExistingIdReturnsUpdatedCustomerDetails() {
		CustomerDTO customerDTO = CustomerDTO.builder()
				.id(1L)
				.firstName("Neil")
				.surname("Jenner")
				.mobileNumber(1234567890L)
				.smoothiePreference("Strawberry").build();
		CustomerDTO updatedCustomer = CustomerDTO.builder()
				.id(1L)
				.firstName("Neil")
				.surname("Jenner")
				.mobileNumber(1234567890L)
				.smoothiePreference("Strawberry").build();
		Mockito.when(customerService.updateCustomer(1L, customerDTO)).thenReturn(updatedCustomer);
		ResponseEntity<?> response = customerController.updateCustomer(1L, customerDTO);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(updatedCustomer, response.getBody());
	}

	@Test
	public void testUpdateCustomerNonExistingIdThrowsException() {
		CustomerDTO customerDTO = CustomerDTO.builder()
				.id(1L)
				.firstName("Neil")
				.surname("Jenner")
				.mobileNumber(1234567890L)
				.smoothiePreference("Strawberry").build();
		Mockito.when(customerService.updateCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(null);
		Assertions.assertThrows(ResponseStatusException.class, () -> {
			customerController.updateCustomer(1L, customerDTO);
		});
	}

	@Test
	public void testDeleteCustomerExistingIdReturnsSuccessMessage() {
		Mockito.when(customerService.deleteCustomer(1L)).thenReturn("Customer successfully deleted");
		ResponseEntity<?> response = customerController.deleteCustomer(1L);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals("Customer successfully deleted", response.getBody());
	}

	@Test
	public void testDeleteCustomerNonExistingIdThrowsException() {
		Mockito.when(customerService.deleteCustomer(anyLong())).thenReturn(null);
		Assertions.assertThrows(ResponseStatusException.class, () -> {
			customerController.deleteCustomer(1L);
		});
	}
}