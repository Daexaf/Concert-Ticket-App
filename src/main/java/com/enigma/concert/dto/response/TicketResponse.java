package com.enigma.concert.dto.response;

import com.enigma.concert.entity.Concert;
import com.enigma.concert.constant.TicketType;
import com.enigma.concert.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TicketResponse {
    private String id;
    private String ticketType;
    private Integer price;
    private Integer stock;
    private Order order;
    private Concert concert;
}
