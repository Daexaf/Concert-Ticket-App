package com.enigma.concert.service;

import com.enigma.concert.dto.request.TicketRequest;
import com.enigma.concert.dto.response.TicketResponse;

import java.util.List;

public interface TicketService {
    TicketResponse createTicket(TicketRequest ticketRequest);

    TicketResponse updateTicket(TicketRequest ticketRequest);

    void deleteTicket(String id);

    List<TicketResponse> getAllTicket();

    TicketResponse getById(String id);
}
