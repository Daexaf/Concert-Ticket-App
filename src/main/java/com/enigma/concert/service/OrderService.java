package com.enigma.concert.service;

import com.enigma.concert.dto.request.OrderRequest;
import com.enigma.concert.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);

    List<OrderResponse> getAllOrder();

    OrderResponse getById(String id);
}
