package com.enigma.concert.dto.request;

import com.enigma.concert.entity.Order;
import com.enigma.concert.entity.Ticket;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OrderDetailRequest {
    private String orderDetailId;
    private Order order;
    private Ticket ticket;
    private Integer quantity;
}
