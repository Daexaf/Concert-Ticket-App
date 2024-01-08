package com.enigma.concert.controller;

import com.enigma.concert.constant.AppPath;
import com.enigma.concert.dto.request.TicketRequest;
import com.enigma.concert.dto.response.CommonResponse;
import com.enigma.concert.dto.response.CustomerResponse;
import com.enigma.concert.dto.response.TicketResponse;
import com.enigma.concert.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Controller
@RequestMapping(AppPath.TICKET)
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/v1")
    public ResponseEntity<?> createTicket(@RequestBody TicketRequest ticketRequest){
        TicketResponse ticketResponse = ticketService.createTicket(ticketRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<TicketResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully created new Ticket")
                        .data(ticketResponse)
                        .build());
    }

    @GetMapping("/v1")
    public List<TicketResponse> getAllTicket(){
        return ticketService.getAllTicket();
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<?> getByIdTicket(@PathVariable String id){
            TicketResponse ticketResponse = ticketService.getById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(CommonResponse.<TicketResponse>builder()
                            .statusCode(HttpStatus.CREATED.value())
                            .message("Successfully get ticket by id")
                            .data(ticketResponse)
                            .build());
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable String id){
        ticketService.deleteTicket(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.<String>builder()
                        .message("Successfully deleted ticket")
                        .data("OK")
                        .build());
    }

    @PutMapping("/v1")
    public ResponseEntity<?> updateTicket(@RequestBody TicketRequest ticketRequest){
        TicketResponse ticketResponse = ticketService.updateTicket(ticketRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<TicketResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully updated ticket")
                        .data(ticketResponse)
                        .build());
    }
}
