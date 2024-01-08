package com.enigma.concert.service.impl;


import com.enigma.concert.dto.request.OrderRequest;
import com.enigma.concert.dto.response.CustomerResponse;
import com.enigma.concert.dto.response.OrderDetailResponse;
import com.enigma.concert.dto.response.OrderResponse;
import com.enigma.concert.dto.response.TicketResponse;
import com.enigma.concert.entity.*;
import com.enigma.concert.repository.OrderDetailRepository;
import com.enigma.concert.repository.OrderRepository;
import com.enigma.concert.repository.TicketRepository;
import com.enigma.concert.service.CustomerService;
import com.enigma.concert.service.OrderDetailService;
import com.enigma.concert.service.OrderService;
import com.enigma.concert.service.TicketService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Transactional(rollbackOn = Exception.class)
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final OrderDetailService orderDetailService;
    private final TicketRepository ticketRepository;
    private final OrderDetailRepository orderDetailRepository;
    @Override
    @Transactional(rollbackOn = Exception.class)
    public OrderResponse createOrder(OrderRequest orderRequest) {
        CustomerResponse customerResponse = customerService.getByIdCustomer(orderRequest.getCustomerId());

        if (customerResponse == null) {
            return null;
        }

        Order order = orderRepository.saveAndFlush(Order.builder()
                .customer(Customer.builder()
                        .id(customerResponse.getId())
                        .name(customerResponse.getName())
                        .email(customerResponse.getEmail())
                        .mobilePhone(customerResponse.getMobilePhone())
                        .build())
                .build());

        List<OrderDetail> orderDetails = orderRequest.getOrderDetails().stream()
                .map(orderDetailRequest -> {
                    Ticket ticket = ticketRepository.findById(orderDetailRequest.getTicketId()).orElse(null);

                    // Validasi ticket
                    if (ticket == null) {
                        return null;
                    }
                    return OrderDetail.builder()
                            .order(order)
                            .ticket(ticket)
                            .quantity(orderDetailRequest.getQuantity())
                            .build();

                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Integer totalQuantity = orderDetails.stream()
                .mapToInt(OrderDetail::getQuantity)
                .sum();

        order.setOrderDetails(orderDetails);
        order.setQuantity(totalQuantity);

        orderRepository.saveAndFlush(order);
        orderDetailRepository.saveAll(orderDetails);


        return OrderResponse.builder()
                .id(order.getId())
                .customerId(Customer.builder()
                        .id(customerResponse.getId())
                        .name(customerResponse.getName())
                        .email(customerResponse.getEmail())
                        .mobilePhone(customerResponse.getMobilePhone())
                        .build())
                .orderDetails(orderDetails)
                .quantity(totalQuantity)
                .build();
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        return null;
    }

    @Override
    public OrderResponse getById(String id) {
        return null;
    }

}
