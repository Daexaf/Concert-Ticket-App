package com.enigma.concert.service.impl;

import com.enigma.concert.dto.request.ConcertRequest;
import com.enigma.concert.dto.response.ConcertResponse;
import com.enigma.concert.entity.Concert;
import com.enigma.concert.repository.ConcertRepository;
import com.enigma.concert.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConcertServiceImpl implements ConcertService {

    private final ConcertRepository concertRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    @Override
    public ConcertResponse createConcert(ConcertRequest concertRequest) {
        Concert concert = Concert.builder()
                .id(concertRequest.getConcertId())
                .eventName(concertRequest.getEventName())
                .schedule(concertRequest.getSchedule())
                .venue(concertRequest.getVenue())
                .build();
        concertRepository.save(concert);
        return getConcertResponse(concert);
    }

    @Override
    public ConcertResponse updateConcert(ConcertRequest concertRequest) {
        Concert currentConcertId = concertRepository.findById(concertRequest.getConcertId()).orElse(null);
        if (currentConcertId != null){
            Concert concert = Concert.builder()
                    .id(concertRequest.getConcertId())
                    .eventName(concertRequest.getEventName())
                    .schedule(concertRequest.getSchedule())
                    .venue(concertRequest.getVenue())
                    .build();
            concertRepository.save(concert);
            return getConcertResponse(concert);
        }
        return null;
    }

    @Override
    public void deleteConcert(String id) {
        concertRepository.deleteById(id);
    }

    @Override
    public List<ConcertResponse> getAllConcert() {
        List<Concert> concerts = concertRepository.findAll();
        return  concerts.stream().map(this::getConcertResponse).collect(Collectors.toList());
    }

    @Override
    public ConcertResponse getByIdConcert(String id) {
        Concert concert = concertRepository.findById(id).orElse(null);
        assert concert != null;
        return getConcertResponse(concert);
    }

    private ConcertResponse getConcertResponse(Concert concert) {
        return ConcertResponse.builder()
                .id(concert.getId())
                .eventName(concert.getEventName())
                .schedule(formatLocalDateTimeToString(concert.getSchedule()))
                .venue(concert.getVenue())
                .build();
    }

    private String formatLocalDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(DATE_FORMATTER);
    }
}
