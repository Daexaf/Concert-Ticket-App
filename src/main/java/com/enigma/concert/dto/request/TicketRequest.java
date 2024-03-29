package com.enigma.concert.dto.request;

import com.enigma.concert.entity.Concert;
import com.enigma.concert.entity.Order;
import com.enigma.concert.constant.TicketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TicketRequest {
    private String id;
    private String ticketType;
    private Integer price;
    private Integer stock;
    private String concertId;
    private String ticketCode;
}
