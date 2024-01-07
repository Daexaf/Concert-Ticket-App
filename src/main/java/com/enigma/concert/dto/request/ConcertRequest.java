package com.enigma.concert.dto.request;

import com.enigma.concert.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ConcertRequest {
    private String concertId;
    private String eventName;
    private LocalDateTime schedule;
    private String venue;
}
