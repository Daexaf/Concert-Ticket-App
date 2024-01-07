package com.enigma.concert.controller;

import com.enigma.concert.constant.AppPath;
import com.enigma.concert.dto.request.CustomerRequest;
import com.enigma.concert.dto.response.CustomerResponse;
import com.enigma.concert.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.CUSTOMER)
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/v1")
    public CustomerResponse createCustomer(@RequestBody CustomerRequest customerRequest){
        return customerService.createCustomer(customerRequest);
    }

    @GetMapping("/v1")
    public List<CustomerResponse> getCustomer(){
        return customerService.getAllCustomer();
    }

    @GetMapping("/v1/{id}")
    public CustomerResponse getIdCustomer(@PathVariable String id){
        return customerService.getByIdCustomer(id);
    }

    @DeleteMapping("/v1/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCustomer(@PathVariable String id){
        customerService.deleteCustomer(id);
    }

    @PutMapping("/v1")
    public CustomerResponse updateCustomer(@RequestBody CustomerRequest customerRequest){
        return customerService.updateCustomer(customerRequest);
    }
}
