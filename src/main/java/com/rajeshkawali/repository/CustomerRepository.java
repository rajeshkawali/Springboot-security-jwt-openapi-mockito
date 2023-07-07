package com.rajeshkawali.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rajeshkawali.entity.Customer;

/**
 * @author Rajesh_Kawali
 *
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
