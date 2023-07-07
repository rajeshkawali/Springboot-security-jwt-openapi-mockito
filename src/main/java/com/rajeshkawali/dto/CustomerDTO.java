
package com.rajeshkawali.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rajesh_Kawali
 * 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "firstName", "surname", "smoothiePreference", "mobileNumber" })
public class CustomerDTO {

	@JsonProperty("id")
	private Long id;

	@NotBlank(message = "First name shouldn't be null or empty")
	@JsonProperty("firstName")
	private String firstName;

	@NotNull(message = "Surname shouldn't be null")
    @NotEmpty(message = "Surname shouldn't be empty")
	@JsonProperty("surname")
	private String surname;

	@NotBlank(message = "Smoothie Preference shouldn't be null or empty")
	@JsonProperty("smoothiePreference")
	private String smoothiePreference;

	//@Size(max = 15, min = 10, message = "Invalid mobile number")
	//@Pattern(regexp = "^\\d{10}$", message = "Invalid mobile number")
	@Digits(message = "Invalid mobile number", fraction = 0, integer = 10)
	@JsonProperty("mobileNumber")
	private Long mobileNumber;
}