package com.enigma.concert.service;

import com.enigma.concert.dto.request.ConcertRequest;
import com.enigma.concert.dto.response.ConcertResponse;

import java.util.List;

public interface ConcertService {

    ConcertResponse createConcert(ConcertRequest concertRequest);

    ConcertResponse updateConcert(ConcertRequest concertRequest);

    void deleteConcert(String id);

    List<ConcertResponse> getAllConcert();

    ConcertResponse getByIdConcert(String id);
}
