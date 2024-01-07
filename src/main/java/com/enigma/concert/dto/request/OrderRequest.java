package com.enigma.concert.dto.request;

import com.enigma.concert.dto.response.TicketResponse;
import com.enigma.concert.entity.Concert;
import com.enigma.concert.entity.Customer;
import com.enigma.concert.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OrderRequest {
    private String orderId;
    private Customer customer;
    private List<OrderDetail> orderDetails;
    private TicketResponse ticketId;
    private Integer quantity;
}
