package com.rajeshkawali.service;

import java.util.List;

import com.rajeshkawali.dto.CustomerDTO;

/**
 * @author Rajesh_Kawali
 *
 */
public interface CustomerService {

	public List<CustomerDTO> getAllCustomers();
	
	public CustomerDTO addCustomer(CustomerDTO customerDto);

	public CustomerDTO customerById(Long id);

	public String deleteCustomer(Long id);

	public CustomerDTO updateCustomer(Long id, CustomerDTO user);

}
