package com.enigma.concert.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "m_concert")
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "schedule", nullable = false)
    private LocalDateTime schedule;
    @Column(name = "venue")
    private String venue;
}
