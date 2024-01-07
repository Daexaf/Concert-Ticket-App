package com.enigma.concert.service;

import com.enigma.concert.dto.request.CustomerRequest;
import com.enigma.concert.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest customerRequest);

    CustomerResponse updateCustomer(CustomerRequest customerRequest);

    void deleteCustomer(String id);

    List<CustomerResponse> getAllCustomer();

    CustomerResponse getByIdCustomer(String id);
}
