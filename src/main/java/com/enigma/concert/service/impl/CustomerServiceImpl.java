package com.enigma.concert.service.impl;

import com.enigma.concert.dto.request.CustomerRequest;
import com.enigma.concert.dto.response.CustomerResponse;
import com.enigma.concert.entity.Customer;
import com.enigma.concert.repository.CustomerRepository;
import com.enigma.concert.service.CustomerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class CustomerServiceImpl implements CustomerService {

    @PersistenceContext
    private EntityManager em;
    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        String nativeQuery = "INSERT INTO m_customer(id, customer_code, full_name, mobile_phone, email)" + "VALUES(?,?,?,?,?)";
        em.createNativeQuery(nativeQuery)
                .setParameter(1, UUID.randomUUID().toString())
                .setParameter(2, customerRequest.getCustomerCode())
                .setParameter(3, customerRequest.getName())
                .setParameter(4, customerRequest.getMobilePhone())
                .setParameter(5, customerRequest.getEmail())
                .executeUpdate();

        Customer customer = customerRepository.findByCustomerCode(customerRequest.getCustomerCode());

        if (customer != null) {
            return CustomerResponse.builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .email(customer.getEmail())
                    .mobilePhone(customer.getMobilePhone())
                    .customerCode(customer.getCustomerCode())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public CustomerResponse updateCustomer(CustomerRequest customerRequest) {
        Query findCustomerQuery = em.createNativeQuery(
                        "SELECT * FROM m_customer WHERE id = ?", Customer.class)
                .setParameter(1, customerRequest.getId());

        List<Customer> resultList = findCustomerQuery.getResultList();
        if (!resultList.isEmpty()) {
            Customer existingCustomer = resultList.get(0);

            Query updateQuery = em.createNativeQuery(
                            "UPDATE m_customer SET full_name = ?, mobile_phone = ?, email = ?, customer_code=? WHERE id = ?")
                    .setParameter(1, customerRequest.getName())
                    .setParameter(2, customerRequest.getMobilePhone())
                    .setParameter(3, customerRequest.getEmail())
                    .setParameter(4, customerRequest.getCustomerCode())
                    .setParameter(5, customerRequest.getId());
            updateQuery.executeUpdate();

            return CustomerResponse.builder()
                    .id(customerRequest.getId())
                    .name(customerRequest.getName())
                    .mobilePhone(customerRequest.getMobilePhone())
                    .email(customerRequest.getEmail())
                    .customerCode(customerRequest.getCustomerCode())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public void deleteCustomer(String id) {
        try{
            Query nativeQuery = em.createNativeQuery("DELETE FROM m_customer WHERE id=?")
                    .setParameter(1, id);
            int deletedRows = nativeQuery.executeUpdate();

            if (deletedRows > 0) {
                System.out.println("Delete Customer Success");
            }else{
                System.out.println("ID Not found");
            }
        }catch (PersistenceException e){
            System.out.println("Error deleting customer " + e.getMessage());
        }
    }

    @Override
    public List<CustomerResponse> getAllCustomer() {
        String nativeQuery = "SELECT id, full_name, mobile_phone, email, customer_code FROM m_customer";
        Query query = em.createNativeQuery(nativeQuery);

        List<Object[]> results = query.getResultList();

        List<CustomerResponse> customerResponses = results.stream()
                .map(result -> CustomerResponse.builder()
                        .id((String) result[0])
                        .name((String) result[1])
                        .mobilePhone((String) result[2])
                        .email((String) result[3])
                        .customerCode((String) result[4])
                        .build())
                .collect(Collectors.toList());

        return customerResponses;
    }

    @Override
    public CustomerResponse getByIdCustomer(String id) {
        String nativeQuery = "SELECT id, full_name, mobile_phone, email FROM m_customer WHERE id = ?";
        Query query = em.createNativeQuery(nativeQuery)
                .setParameter(1, id);

        List<Object[]> results = query.getResultList();

        if (results.isEmpty()) {
            return null;
        }

        if (results.size() > 1){
            System.out.println("error");
        }

        Object[] result = results.get(0);

        return CustomerResponse.builder()
                .id((String) result[0])
                .name((String) result[1])
                .mobilePhone((String) result[2])
                .email((String) result[3])
                .build();
    }
}
