package com.enigma.concert.controller;

import com.enigma.concert.constant.AppPath;
import com.enigma.concert.dto.request.OrderRequest;
import com.enigma.concert.dto.request.TicketRequest;
import com.enigma.concert.dto.response.OrderResponse;
import com.enigma.concert.dto.response.TicketResponse;
import com.enigma.concert.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.ORDER)
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/v1")
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest){
        return orderService.createOrder(orderRequest);
    }

    @GetMapping("/v1")
    public List<TicketResponse> getAllTicket(){
        return null;
    }

//    @GetMapping("/v1/{id}")
//    public TicketResponse getByIdTicket(@PathVariable String id){
//        return ticketService.getById(id);
//    }
}
