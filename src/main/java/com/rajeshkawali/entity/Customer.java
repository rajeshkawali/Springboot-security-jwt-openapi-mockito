
package com.rajeshkawali.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
@Entity
@Table(name = "CustomerInventory")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "firstName", length = 25, nullable = false)
	private String firstName;

	@Column(name = "surname", length = 15, nullable = false)
	private String surname;

	@Column(name = "smoothiePreference")
	private String smoothiePreference;

	@Column(name = "mobileNumber", length = 10)
	private Long mobileNumber;
}