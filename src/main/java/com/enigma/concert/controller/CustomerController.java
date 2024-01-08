package com.enigma.concert.controller;

import com.enigma.concert.constant.AppPath;
import com.enigma.concert.dto.request.CustomerRequest;
import com.enigma.concert.dto.response.CommonResponse;
import com.enigma.concert.dto.response.ConcertResponse;
import com.enigma.concert.dto.response.CustomerResponse;
import com.enigma.concert.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping(AppPath.CUSTOMER)
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/v1")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse = customerService.createCustomer(customerRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<CustomerResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully created new Customer")
                        .data(customerResponse)
                        .build());
    }

    @GetMapping("/v1")
    public List<CustomerResponse> getCustomer(){
        return customerService.getAllCustomer();
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<?> getIdCustomer(@PathVariable String id){
        CustomerResponse customerResponse = customerService.getByIdCustomer(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<CustomerResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully get customer by id")
                        .data(customerResponse)
                        .build());
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id){
        customerService.deleteCustomer(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.<String>builder()
                        .message("Successfully deleted customer")
                        .data("OK")
                        .build());
    }

    @PutMapping("/v1")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse = customerService.updateCustomer(customerRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<CustomerResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully updated customer")
                        .data(customerResponse)
                        .build());
    }
}
