package com.rajeshkawali.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.rajeshkawali.dto.CustomerDTO;
import com.rajeshkawali.entity.Customer;

/**
 * @author Rajesh_Kawali
 * 
 */
@Component
public class Util {

	public static CustomerDTO entityToDto(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();
		if (customer != null) {
			customerDTO.setId(customer.getId());
			customerDTO.setFirstName(customer.getFirstName());
			customerDTO.setSurname(customer.getSurname());
			customerDTO.setSmoothiePreference(customer.getSmoothiePreference());
			customerDTO.setMobileNumber(customer.getMobileNumber());
			return customerDTO;
		}
		return customerDTO;
	}

	public static Customer dtoToEntity(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		if (customerDTO != null) {
			customer.setId(customerDTO.getId());
			customer.setFirstName(customerDTO.getFirstName());
			customer.setSurname(customerDTO.getSurname());
			customer.setSmoothiePreference(customerDTO.getSmoothiePreference());
			customer.setMobileNumber(customerDTO.getMobileNumber());
			return customer;
		}
		return customer;
	}

	public static void createCustomerEntity(CustomerDTO customerToUpdate, CustomerDTO customerDetails) {
		if (checkEmptyNullString(customerToUpdate.getFirstName())
				&& !customerToUpdate.getFirstName().equals(customerDetails.getFirstName())) {
			customerDetails.setFirstName(customerToUpdate.getFirstName());
		}
		if (checkEmptyNullString(customerToUpdate.getSurname())
				&& !customerToUpdate.getSurname().equals(customerDetails.getSurname())) {
			customerDetails.setSurname(customerToUpdate.getSurname());
		}
		if ((customerToUpdate.getMobileNumber() != null)
				&& !customerToUpdate.getMobileNumber().equals(customerDetails.getMobileNumber())) {
			customerDetails.setMobileNumber(customerToUpdate.getMobileNumber());
		}
		if (checkEmptyNullString(customerToUpdate.getSmoothiePreference())
				&& !customerToUpdate.getSmoothiePreference().equals(customerDetails.getSmoothiePreference())) {
			customerDetails.setSmoothiePreference(customerToUpdate.getSmoothiePreference());
		}
	}

	public static boolean checkEmptyNullString(String input) {
		return !StringUtils.isEmpty(input) && !StringUtils.isEmpty(input.trim());
	}

}