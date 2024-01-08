package com.enigma.concert.repository;

import com.enigma.concert.entity.Customer;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByCustomerCode(String customerCode);

}
