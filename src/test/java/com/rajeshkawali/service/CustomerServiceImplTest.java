package com.rajeshkawali.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rajeshkawali.constant.CustomerConstants;
import com.rajeshkawali.dto.CustomerDTO;
import com.rajeshkawali.entity.Customer;
import com.rajeshkawali.repository.CustomerRepository;
import com.rajeshkawali.util.Util;


/**
 * @author Rajesh_Kawali
 *
 */
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(CustomerServiceImpl.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration
@WithMockUser(username="admin",roles={"USER","ADMIN"})
public class CustomerServiceImplTest {

	@InjectMocks
	private CustomerServiceImpl customerService;

	@MockBean
	private CustomerRepository customerRepository;

	@Test
	public void testGetAllCustomersReturnsListOfCustomers() {
		List<Customer> mockCustomers = new ArrayList<>();
		mockCustomers.add(Customer.builder()
				.id(1L)
				.firstName("Neil")
				.surname("Jenner")
				.mobileNumber(1234567890L)
				.smoothiePreference("Strawberry").build());
		mockCustomers.add(Customer.builder()
				.id(2L)
				.firstName("Jane")
				.surname("Smith")
				.mobileNumber(9876543210L)
				.smoothiePreference("Banana").build());
		Mockito.when(customerRepository.findAll()).thenReturn(mockCustomers);
		List<CustomerDTO> response = customerService.getAllCustomers();
		Assertions.assertEquals(mockCustomers.size(), response.size());
		Assertions.assertEquals(mockCustomers.get(0).getId(), response.get(0).getId());
		Assertions.assertEquals(mockCustomers.get(1).getId(), response.get(1).getId());
	}
	
	@Test
    public void testGetAllCustomers_ExceptionThrown() {
        Mockito.when(customerRepository.findAll()).thenThrow(new RuntimeException("Failed to get customers"));
        List<CustomerDTO> customerList = customerService.getAllCustomers();
        Assertions.assertNotEquals(null,customerList);
    }

	@Test
	public void testAddCustomerReturnsAddedCustomerDetails() {
		CustomerDTO customerDTO = CustomerDTO.builder()
				.firstName("Neil")
				.surname("Jenner")
				.mobileNumber(1234567890L)
				.smoothiePreference("Strawberry").build();
		Customer addedCustomer = Customer.builder()
				.id(1L)
				.firstName("Neil")
				.surname("Jenner")
					.mobileNumber(1234567890L)
				.smoothiePreference("Strawberry").build();
		Mockito.when(customerRepository.save(Mockito.any())).thenReturn(addedCustomer);
		CustomerDTO response = customerService.addCustomer(customerDTO);
		Assertions.assertEquals(addedCustomer.getId(), response.getId());
		Assertions.assertEquals(addedCustomer.getFirstName(), response.getFirstName());
		Assertions.assertEquals(addedCustomer.getSurname(), response.getSurname());
		Assertions.assertEquals(addedCustomer.getMobileNumber(), response.getMobileNumber());
		Assertions.assertEquals(addedCustomer.getSmoothiePreference(), response.getSmoothiePreference());
	}
	
	@Test
    public void testAddCustomerExceptionThrown() {
        Mockito.doThrow(new RuntimeException("Failed to save customer")).when(customerRepository).save(Mockito.any());
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("Rajesh")
                .surname("Kawali")
                .mobileNumber(1234567890L)
                .smoothiePreference("Strawberry")
                .build();
        CustomerDTO customerDTOResponse = customerService.addCustomer(customerDTO);
        Assertions.assertEquals(CustomerDTO.builder().build(), customerDTOResponse);
    }

	@Test
	public void testCustomerByIdExistingIdReturnsCustomerDetails() {
		Customer mockCustomer = Customer.builder()
				.id(1L)
				.firstName("Neil")
				.surname("Jenner")
				.mobileNumber(1234567890L)
				.smoothiePreference("Strawberry").build();
		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(mockCustomer));
		CustomerDTO response = customerService.customerById(1L);
		Assertions.assertEquals(mockCustomer.getId(), response.getId());
		Assertions.assertEquals(mockCustomer.getFirstName(), response.getFirstName());
		Assertions.assertEquals(mockCustomer.getSurname(), response.getSurname());
		Assertions.assertEquals(mockCustomer.getMobileNumber(), response.getMobileNumber());
		Assertions.assertEquals(mockCustomer.getSmoothiePreference(), response.getSmoothiePreference());
	}

	@Test
	public void testCustomerByIdNonExistingIdReturnsNull() {
		Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		CustomerDTO response = customerService.customerById(1L);
		Assertions.assertNull(response);
	}

	@Test
	public void testDeleteCustomerExistingIdReturnsDeleteMessage() {
		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(Customer.builder().build()));
		String response = customerService.deleteCustomer(1L);
		Assertions.assertEquals(CustomerConstants.DELETE_MESSAGE, response);
	}

	@Test
	public void testDeleteCustomerExceptionThrown() {
		Mockito.doThrow(new RuntimeException()).when(customerRepository).deleteById(Mockito.anyLong());
		//Mockito.doThrow(new RuntimeException()).when(customerService).customerById(Mockito.anyLong());;
		//Mockito.when(customerService.customerById(1L)).thenThrow(new RuntimeException("exception"));
		//Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		String response = customerService.deleteCustomer(1L);
		Assertions.assertNull(response);
	}

	@Test
	public void testUpdateCustomerExistingIdReturnsUpdatedCustomerDetails() {
		Customer customerToSave = Customer.builder()
				.id(1L)
				.firstName("Neil")
				.surname("Jenner")
				.mobileNumber(1234567890L)
				.smoothiePreference("Strawberry").build();
		CustomerDTO customerToUpdate = CustomerDTO.builder()
				.id(1L)
				.firstName("Neil")
				.surname("Jenner")
				.mobileNumber(1234567890L)
				.smoothiePreference("Strawberry").build();
		CustomerDTO customerDetails = CustomerDTO.builder()
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
		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(Util.dtoToEntity(customerDetails)));
	    Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customerToSave);
		CustomerDTO response = customerService.updateCustomer(1L, customerToUpdate);
		Assertions.assertEquals(updatedCustomer.getId(), response.getId());
		Assertions.assertEquals(updatedCustomer.getFirstName(), response.getFirstName());
		Assertions.assertEquals(updatedCustomer.getSurname(), response.getSurname());
		Assertions.assertEquals(updatedCustomer.getMobileNumber(), response.getMobileNumber());
		Assertions.assertEquals(updatedCustomer.getSmoothiePreference(), response.getSmoothiePreference());
	}

	@Test
	public void testUpdateCustomerNonExistingIdReturnsNull() {
		Mockito.when(customerService.customerById(Mockito.anyLong())).thenReturn(null);
		CustomerDTO response = customerService.updateCustomer(1L, CustomerDTO.builder().build());
		Assertions.assertNull(response);
	}
	
	@Test
    public void testUpdateCustomer_CustomerNotFound() {
		//Mockito.doThrow(new RuntimeException()).when(customerRepository).deleteById(Mockito.anyLong());
		//Mockito.doThrow(new RuntimeException()).when(customerService).addCustomer(Mockito.any());
		//Mockito.doThrow(new RuntimeException()).when(customerService).customerById(Mockito.anyLong());
		Mockito.when(customerService.customerById(1L)).thenThrow(new RuntimeException("exception"));
        CustomerDTO customerToUpdate = CustomerDTO.builder()
                .firstName("John")
                .surname("Doe")
                .mobileNumber(1234567890L)
                .smoothiePreference("Strawberry")
                .build();
        CustomerDTO updatedCustomer = customerService.updateCustomer(1L, customerToUpdate);
        Assertions.assertNull(updatedCustomer);
    }
}
