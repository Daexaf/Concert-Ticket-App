package com.enigma.concert.dto.response;

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
public class ConcertResponse {
    private String id;
    private String eventName;
    private String schedule;
    private String venue;
    private String concertCode;
}
