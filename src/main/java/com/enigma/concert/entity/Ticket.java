package com.enigma.concert.entity;

import com.enigma.concert.constant.TicketType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "m_ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;
    @Column(name = "price")
    private Integer price;
    @Column(name = "stock", columnDefinition = "int check (stock > 0)")
    private Integer stock;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "concert_id")
    private Concert concert;
    @Column(name = "ticket_code")
    private String ticketCode;
}
