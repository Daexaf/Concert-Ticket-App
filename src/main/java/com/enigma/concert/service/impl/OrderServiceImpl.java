package com.enigma.concert.service.impl;


import com.enigma.concert.dto.request.OrderRequest;
import com.enigma.concert.dto.response.CustomerResponse;
import com.enigma.concert.dto.response.OrderResponse;
import com.enigma.concert.dto.response.TicketResponse;
import com.enigma.concert.entity.*;
import com.enigma.concert.repository.OrderDetailRepository;
import com.enigma.concert.repository.OrderRepository;
import com.enigma.concert.repository.TicketRepository;
import com.enigma.concert.service.CustomerService;
import com.enigma.concert.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional(rollbackOn = Exception.class)
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final TicketRepository ticketRepository;
    private final OrderDetailRepository orderDetailRepository;


    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        CustomerResponse customerResponse = customerService.getByIdCustomer(orderRequest.getOrderId());

        if (customerResponse == null){
            return null;
        }

        Order order = orderRepository.save(Order.builder()
                        .customer(Customer.builder()
                                .id(customerResponse.getId())
                                .name(customerResponse.getName())
                                .email(customerResponse.getEmail())
                                .mobilePhone(customerResponse.getMobilePhone())
                                .build())
                .build());
        orderRepository.saveAndFlush(order);

        Ticket ticket = ticketRepository.findById(orderRequest.getTicketId().getId()).orElse(null);

        OrderDetail orderDetail = orderDetailRepository.save(OrderDetail.builder()
                        .order(order)
                        .ticket(ticket)
                        .quantity(orderRequest.getQuantity())
                .build());
        orderDetailRepository.save(orderDetail);


        assert ticket != null;
        return OrderResponse.builder()
                .id(order.getId())
                .customer(Customer.builder()
                        .id(customerResponse.getId())
                        .name(customerResponse.getName())
                        .email(customerResponse.getEmail())
                        .mobilePhone(customerResponse.getMobilePhone())
                        .build())
                .orderDetails(orderDetail.getOrder().getOrderDetails())
                .ticketId(TicketResponse.builder()
                        .id(ticket.getId())
                        .price(ticket.getPrice())
                        .order(Order.builder()
                                .id(order.getId())
                                .customer(order.getCustomer())
                                .orderDetails(orderDetail.getOrder().getOrderDetails())
                                .build())
                        .build())
                .build();
    }

    @Override
    public List<OrderResponse> getAllOrder() {return null;
    }

    @Override
    public OrderResponse getById(String id) {
        return null;
    }

}
