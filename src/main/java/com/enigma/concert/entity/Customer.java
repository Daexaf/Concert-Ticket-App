package com.enigma.concert.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "m_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "full_name", nullable = false, length = 100)
    private String name;
    @Column(name = "mobile_phone", nullable = false, unique = true ,length = 15)
    private String mobilePhone;
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Column(name = "customer_code")
    private String customerCode;
}
