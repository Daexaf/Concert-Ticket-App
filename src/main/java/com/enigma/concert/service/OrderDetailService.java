package com.enigma.concert.service;

import com.enigma.concert.dto.request.ConcertRequest;
import com.enigma.concert.dto.request.OrderDetailRequest;
import com.enigma.concert.dto.response.ConcertResponse;
import com.enigma.concert.dto.response.OrderDetailResponse;

import java.util.List;

public interface OrderDetailService {

    OrderDetailResponse createOrderDetail(OrderDetailRequest orderDetailRequest);

    List<OrderDetailResponse> getAllOrderDetail();

    OrderDetailResponse getByIdOrderDetail(String id);
}
