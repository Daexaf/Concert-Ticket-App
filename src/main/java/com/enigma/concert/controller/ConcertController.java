package com.enigma.concert.controller;

import com.enigma.concert.constant.AppPath;
import com.enigma.concert.dto.request.ConcertRequest;
import com.enigma.concert.dto.response.ConcertResponse;
import com.enigma.concert.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.CONCERT)
public class ConcertController {
    private final ConcertService concertService;

    @PostMapping("/v1")
    public ConcertResponse createConcert(@RequestBody ConcertRequest concertRequest){
        return concertService.createConcert(concertRequest);
    }
    @GetMapping("/v1")
    public List<ConcertResponse> getConcert(){
        return concertService.getAllConcert();
    }

    @GetMapping("/v1/{id}")
    public ConcertResponse getIdConcert(@PathVariable String id){
        return concertService.getByIdConcert(id);
    }

    @DeleteMapping("/v1/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCustomer(@PathVariable String id){
        concertService.deleteConcert(id);
    }

    @PutMapping("/v1")
    public ConcertResponse updateCustomer(@RequestBody ConcertRequest concertRequest){
        return concertService.updateConcert(concertRequest);
    }
}
