package com.enigma.concert.service.impl;

import com.enigma.concert.dto.request.OrderDetailRequest;
import com.enigma.concert.dto.response.OrderDetailResponse;
import com.enigma.concert.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    @Override
    public OrderDetailResponse createOrderDetail(OrderDetailRequest orderDetailRequest) {
        return null;
    }

    @Override
    public List<OrderDetailResponse> getAllOrderDetail() {
        return null;
    }

    @Override
    public OrderDetailResponse getByIdOrderDetail(String id) {
        return null;
    }
}
