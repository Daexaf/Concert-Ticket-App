package com.enigma.concert.service.impl;

import com.enigma.concert.constant.TicketType;
import com.enigma.concert.dto.request.TicketRequest;
import com.enigma.concert.dto.response.CustomerResponse;
import com.enigma.concert.dto.response.TicketResponse;
import com.enigma.concert.entity.Concert;
import com.enigma.concert.entity.Customer;
import com.enigma.concert.entity.Ticket;
import com.enigma.concert.repository.ConcertRepository;
import com.enigma.concert.repository.TicketRepository;
import com.enigma.concert.service.TicketService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class TicketServiceImpl implements TicketService {
    @PersistenceContext
    private EntityManager em;
    private final TicketRepository ticketRepository;
    private final ConcertRepository concertRepository;

    @Override
    public TicketResponse createTicket(TicketRequest ticketRequest) {
        String nativeQuery = "INSERT INTO m_ticket(id, ticket_type, price, stock, concert_id, ticket_code) VALUES(?,?,?,?,?,?)";
        em.createNativeQuery(nativeQuery)
                .setParameter(1, UUID.randomUUID().toString())
                .setParameter(2, ticketRequest.getTicketType())
                .setParameter(3, ticketRequest.getPrice())
                .setParameter(4, ticketRequest.getStock())
                .setParameter(5, ticketRequest.getConcertId())
                .setParameter(6, ticketRequest.getTicketCode())
                .executeUpdate();

        Ticket ticket = ticketRepository.findByTicketCode(ticketRequest.getTicketCode());

        if (ticket != null) {
            return TicketResponse.builder()
                    .id(ticket.getId())
                    .ticketType(ticket.getTicketType().name())
                    .stock(ticket.getStock())
                    .price(ticket.getPrice())
                    .ticketCode(ticket.getTicketCode())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public TicketResponse updateTicket(TicketRequest ticketRequest) {
        Query findTicketQuery = em.createNativeQuery(
                        "SELECT * FROM m_ticket WHERE id = ?", Ticket.class)
                .setParameter(1, ticketRequest.getId());

        List<Ticket> resultList = findTicketQuery.getResultList();
        if (!resultList.isEmpty()) {
            Ticket existingTicket = resultList.get(0);

            Query updateQuery = em.createNativeQuery(
                            "UPDATE m_ticket SET ticket_code = ?, price = ?, stock = ?, ticket_type = ?, concert_id = ? WHERE id = ?")
                    .setParameter(1, ticketRequest.getTicketCode())
                    .setParameter(2, ticketRequest.getPrice())
                    .setParameter(3, ticketRequest.getStock())
                    .setParameter(4, ticketRequest.getTicketType())
                    .setParameter(5, ticketRequest.getConcertId())
                    .setParameter(6, ticketRequest.getId());

            updateQuery.executeUpdate();

            Ticket updatedTicket = em.find(Ticket.class, ticketRequest.getId());

            return TicketResponse.builder()
                    .id(ticketRequest.getId())
                    .price(ticketRequest.getPrice())
                    .stock(ticketRequest.getStock())
                    .ticketType(String.valueOf(ticketRequest.getTicketType()))
                    .concertId(String.valueOf(updatedTicket.getConcert()))
                    .ticketCode(ticketRequest.getTicketCode())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public void deleteTicket(String id) {
        try{
            Query nativeQuery = em.createNativeQuery("DELETE FROM m_ticket WHERE id=?")
                    .setParameter(1, id);
            int deletedRows = nativeQuery.executeUpdate();

            if (deletedRows > 0) {
                System.out.println("Delete ticket Success");
            }else{
                System.out.println("ID Not found");
            }
        }catch (PersistenceException e){
            System.out.println("Error deleting ticket " + e.getMessage());
        }
    }

    @Override
    public List<TicketResponse> getAllTicket() {
        String nativeQuery = "SELECT t.id, t.ticket_type, t.price, t.stock, t.concert_id, t.ticket_code " +
                "FROM m_ticket t LEFT JOIN m_concert c ON t.concert_id = c.id";
        Query query = em.createNativeQuery(nativeQuery);

        List<Object[]> results = query.getResultList();

        List<TicketResponse> ticketResponses = results.stream()
                .map(result -> TicketResponse.builder()
                        .id((String) result[0])
                        .ticketType((String) result[1])
                        .price((Integer) result[2])
                        .stock((Integer) result[3])
                        .concertId((String) result[4])
                        .ticketCode((String) result[5])
                        .build())
                .collect(Collectors.toList());

        return ticketResponses;
    }

    @Override
    public TicketResponse getById(String id) {
        String nativeQuery = "SELECT id, ticket_type, price, stock, concert_id, ticket_code FROM m_ticket WHERE id = ?";
        Query query = em.createNativeQuery(nativeQuery)
                .setParameter(1, id);

        List<Object[]> results = query.getResultList();

        if (results.isEmpty()) {
            return null;
        }
        if (results.size() > 1){
            System.out.println("error");
        }
        Object[] result = results.get(0);
        return TicketResponse.builder()
                .id((String) result[0])
                .ticketType((String) result[1])
                .price((Integer) result[2])
                .stock((Integer) result[3])
                .concertId((String) result[4])
                .ticketCode((String) result[5])
                .build();
    }

}
