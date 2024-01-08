package com.enigma.concert.controller;

import com.enigma.concert.constant.AppPath;
import com.enigma.concert.dto.request.ConcertRequest;
import com.enigma.concert.dto.response.CommonResponse;
import com.enigma.concert.dto.response.ConcertResponse;
import com.enigma.concert.dto.response.OrderResponse;
import com.enigma.concert.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping(AppPath.CONCERT)
public class ConcertController {
    private final ConcertService concertService;

    @PostMapping("/v1")
    public ResponseEntity<?> createConcert(@RequestBody ConcertRequest concertRequest){
        ConcertResponse concertResponse = concertService.createConcert(concertRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<ConcertResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully created new concert")
                        .data(concertResponse)
                        .build());
    }

    @GetMapping("/v1")
    public List<ConcertResponse> getConcert(){
        return concertService.getAllConcert();
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<?> getIdConcert(@PathVariable String id){
        ConcertResponse concertResponse = concertService.getByIdConcert(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<ConcertResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully created new product")
                        .data(concertResponse)
                        .build());
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id){
        concertService.deleteConcert(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.<String>builder()
                        .message("Successfully created new product")
                        .data("OK")
                        .build());
    }

    @PutMapping("/v1")
    public ResponseEntity<?> updateCustomer(@RequestBody ConcertRequest concertRequest){
        ConcertResponse concertResponse = concertService.updateConcert(concertRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<ConcertResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully created new concert")
                        .data(concertResponse)
                        .build());
    }
}
