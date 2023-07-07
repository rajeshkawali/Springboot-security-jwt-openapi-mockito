package com.rajeshkawali.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajeshkawali.dto.CustomerDTO;
import com.rajeshkawali.exception.ResponseStatus;
import com.rajeshkawali.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Rajesh_Kawali
 *
 */
@Slf4j
@RequestMapping("/api")
@Tag(name = "Customer", description = "Customer management APIs")
@RestController
public class CustomerController {

	public static final String CLASS_NAME = CustomerController.class.getName();

	@Autowired
	private CustomerService customerService;

	@Operation(summary = "Retrieve customers", description = "Get all customers object by specifying its id.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successful retrieval of customers", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class)))),
			@ApiResponse(responseCode = "404", description = "Customer not found.", content = {@Content(schema = @Schema()) }) })
	@GetMapping("/v1/customer/getAll")
	public List<CustomerDTO> getAllCustomers() {
		String _function = ".getAllCustomers";
		log.info(CLASS_NAME + _function + "::ENTER");
		List<CustomerDTO> customerList = new ArrayList<>();
		customerList = customerService.getAllCustomers();
		log.info(CLASS_NAME + _function + "::EXIT");
		return customerList;
	}

	@Operation(summary = "Add a new customer", description = "This api is used to add new customer into DB")
	@ApiResponse(responseCode = "201", description = "Customer successfully added to the DB", content = {
			@Content(schema = @Schema(implementation = CustomerDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE) })
	@PreAuthorize("hasAuthority('admin:create')")
	@PostMapping("/v1/customer/add")
	public ResponseEntity<?> addCustomer(@Parameter(description = "Customer details") @Valid @RequestBody CustomerDTO customerDTO) {
		String _function = ".addCustomer";
		log.info(CLASS_NAME + _function + "::ENTER");
		log.debug(CLASS_NAME + _function + "::Customer details: {}", customerDTO);
		CustomerDTO addedCustomer = customerService.addCustomer(customerDTO);
		log.info(CLASS_NAME + _function + "::EXIT");
		return ResponseEntity.status(HttpStatus.CREATED).body(addedCustomer);

	}

	@Operation(summary = "Retrieve customer", description = "Get customer object by specifying its id.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Customer details for given id", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class)))),
		@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = {@Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "404", description = "Customer not found.", content = {@Content(schema = @Schema()) }) })
	@GetMapping("/v1/customer/{id}")
	public ResponseEntity<?> customerById(@Parameter(description = "Customer id", required = true) @PathVariable Long id) {
		String _function = ".customerById";
		log.info(CLASS_NAME + _function + "::ENTER");
		log.debug(CLASS_NAME + _function + "::Requested customer id: {} ", id);
		CustomerDTO addedCustomer = customerService.customerById(id);
		if (addedCustomer != null) {
			log.info(CLASS_NAME + _function + "::EXIT");
			return ResponseEntity.status(HttpStatus.OK).body(addedCustomer);
		} else {
			log.error(CLASS_NAME + _function + "::Customer not available for given id: {}", id);
			throw ResponseStatus.idNotFound.apply(id);
		}
	}

	@Operation(summary = "Update the customer details", description = "Update customer details by providing the id.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Customer details are successfully updated into the DB", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class)))),
		@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = {@Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "404", description = "Customer not found for the given id", content = {@Content(schema = @Schema()) }) })
	@PreAuthorize("hasAuthority('admin:update')")
	@PutMapping("/v1/customer/{id}")
	public ResponseEntity<?> updateCustomer(@Parameter(description = "Customer id", required = true) @PathVariable Long id, @Parameter(description = "Customer details") @Valid @RequestBody CustomerDTO customerDTO) {
		String _function = ".updateCustomer";
		log.info(CLASS_NAME + _function + "::ENTER");
		log.debug(CLASS_NAME + _function + "::Customer details to update-> id: {}, Customer details: {}", id, customerDTO);
		CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
		if (updatedCustomer != null) {
			log.info(CLASS_NAME + _function + "::EXIT");
			return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
		} else {
			log.error(CLASS_NAME + _function + "::Customer not available for given Id: {} ", id);
			throw ResponseStatus.idNotFound.apply(id);
		}
	}

	@Operation(summary = "Delete customer details", description = "Delete customer object by using its id.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Customer details are successfully deleted from the DB", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class)))),
		@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = {@Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "404", description = "Customer not found for the given id", content = {@Content(schema = @Schema()) }) })
	@PreAuthorize("hasAuthority('admin:delete')")
	@DeleteMapping("/v1/customer/{id}")
	public ResponseEntity<?> deleteCustomer(@Parameter(description = "Customer id", required = true) @PathVariable Long id) {
		String _function = ".deleteCustomer";
		log.info(CLASS_NAME + _function + "::ENTER");
		log.debug(CLASS_NAME + _function + "::Customer id to delete from db: {} ", id);
		String result = customerService.deleteCustomer(id);
		if (result != null) {
			log.info(CLASS_NAME + _function + "::EXIT");
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			log.error(CLASS_NAME + _function + "::Customer not available for given id: {} ", id);
			throw ResponseStatus.idNotFound.apply(id);
		}
	}

}
