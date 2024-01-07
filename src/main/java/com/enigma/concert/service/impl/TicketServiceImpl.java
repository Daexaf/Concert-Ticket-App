package com.enigma.concert.service.impl;

import com.enigma.concert.dto.request.TicketRequest;
import com.enigma.concert.dto.response.TicketResponse;
import com.enigma.concert.entity.Ticket;
import com.enigma.concert.repository.TicketRepository;
import com.enigma.concert.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public TicketResponse createTicket(TicketRequest ticketRequest) {
        Ticket ticket = Ticket.builder()
                .id(ticketRequest.getTicketId())
                .ticketType(ticketRequest.getTicketType())
                .price(ticketRequest.getPrice())
                .concert(ticketRequest.getConcert())
                .build();
        ticketRepository.save(ticket);
        return getTicketResponse(ticket);
    }

    @Override
    public TicketResponse updateTicket(TicketRequest ticketRequest) {
        Ticket currentTicketId = ticketRepository.findById(ticketRequest.getTicketId()).orElse(null);
        if(currentTicketId != null){
            Ticket ticket = Ticket.builder()
                    .id(ticketRequest.getTicketId())
                    .concert(ticketRequest.getConcert())
                    .price(ticketRequest.getPrice())
                    .ticketType(ticketRequest.getTicketType())
                    .build();
            ticketRepository.save(ticket);
            return getTicketResponse(ticket);
        }
        return null;
    }

    @Override
    public void deleteTicket(String id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public List<TicketResponse> getAllTicket() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream().map(this::getTicketResponse).collect(Collectors.toList());
    }

    @Override
    public TicketResponse getById(String id) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        assert ticket != null;
        return getTicketResponse(ticket);
    }

    private TicketResponse getTicketResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .ticketType(ticket.getTicketType().toString())
                .concert(ticket.getConcert())
                .price(ticket.getPrice())
                .build();
    }
}
