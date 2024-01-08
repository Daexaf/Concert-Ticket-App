package com.enigma.concert.repository;

import com.enigma.concert.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {
    Ticket findByTicketCode(String ticketCode);
}
