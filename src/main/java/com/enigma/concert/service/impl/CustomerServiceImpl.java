package com.enigma.concert.service.impl;

import com.enigma.concert.dto.request.CustomerRequest;
import com.enigma.concert.dto.response.CustomerResponse;
import com.enigma.concert.entity.Customer;
import com.enigma.concert.repository.CustomerRepository;
import com.enigma.concert.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .id(customerRequest.getCustomerId())
                .name(customerRequest.getName())
                .mobilePhone(customerRequest.getMobilePhone())
                .email(customerRequest.getEmail())
                .build();
        customerRepository.save(customer);
        return getCustomerResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(CustomerRequest customerRequest) {
        Customer currentCustomerId = customerRepository.findById(customerRequest.getCustomerId()).orElse(null);
        if (currentCustomerId != null){
            Customer customer = Customer.builder()
                    .id(customerRequest.getCustomerId())
                    .name(customerRequest.getName())
                    .mobilePhone(customerRequest.getMobilePhone())
                    .email(customerRequest.getEmail())
                    .build();
            customerRepository.save(customer);
            return getCustomerResponse(customer);
        }
        return null;
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerResponse> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::getCustomerResponse).collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getByIdCustomer(String id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        assert customer != null;
        return getCustomerResponse(customer);
    }

    private CustomerResponse getCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .mobilePhone(customer.getMobilePhone())
                .build();
    }
}
