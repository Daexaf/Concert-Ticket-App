package com.enigma.concert.service.impl;

import com.enigma.concert.dto.request.ConcertRequest;
import com.enigma.concert.dto.response.ConcertResponse;
import com.enigma.concert.dto.response.CustomerResponse;
import com.enigma.concert.entity.Concert;
import com.enigma.concert.entity.Customer;
import com.enigma.concert.repository.ConcertRepository;
import com.enigma.concert.service.ConcertService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class ConcertServiceImpl implements ConcertService {
    @PersistenceContext
    private EntityManager em;
    private final ConcertRepository concertRepository;
    @Override
    public ConcertResponse createConcert(ConcertRequest concertRequest) {
        String nativeQuery = "INSERT INTO m_concert(id, event_name, schedule, venue, concert_code)" + "VALUES(?,?,?,?,?)";


        em.createNativeQuery(nativeQuery)
                .setParameter(1, UUID.randomUUID().toString())
                .setParameter(2, concertRequest.getEventName())
                .setParameter(3, concertRequest.getSchedule())
                .setParameter(4, concertRequest.getVenue())
                .setParameter(5, concertRequest.getConcertCode())
                .executeUpdate();

        Concert concert = concertRepository.findByConcertCode(concertRequest.getConcertCode());

        if (concert != null) {
            return getConcertResponse(concert);
        } else {
            return null;
        }
    }

    @Override
    public ConcertResponse updateConcert(ConcertRequest concertRequest) {
        Query findConcertQuery = em.createNativeQuery(
                        "SELECT * FROM m_concert WHERE id = ?", Concert.class)
                .setParameter(1, concertRequest.getId());

        List<Concert> resultList = findConcertQuery.getResultList();
        if (!resultList.isEmpty()) {
            Concert existingConcert = resultList.get(0);

            Query updateQuery = em.createNativeQuery(
                            "UPDATE m_concert SET concert_code = ?, event_name = ?, schedule = ?, venue = ? WHERE id = ?")
                    .setParameter(1, concertRequest.getConcertCode())
                    .setParameter(2, concertRequest.getEventName())
                    .setParameter(3, concertRequest.getSchedule())
                    .setParameter(4, concertRequest.getVenue())
                    .setParameter(5, concertRequest.getId());

            updateQuery.executeUpdate();

            return ConcertResponse.builder()
                    .id(concertRequest.getId())
                    .eventName(concertRequest.getEventName())
                    .schedule(concertRequest.getSchedule())
                    .venue(concertRequest.getVenue())
                    .concertCode(concertRequest.getConcertCode())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public void deleteConcert(String id) {
        try{
            Query nativeQuery = em.createNativeQuery("DELETE FROM m_concert WHERE id=?")
                    .setParameter(1, id);
            int deletedRows = nativeQuery.executeUpdate();

            if (deletedRows > 0) {
                System.out.println("Delete Concert Success");
            }else{
                System.out.println("ID Not found");
            }
        }catch (PersistenceException e){
            System.out.println("Error deleting concert " + e.getMessage());
        }
    }

    @Override
    public List<ConcertResponse> getAllConcert() {
        String nativeQuery = "SELECT id, event_name, schedule, venue, concert_code FROM m_concert";
        Query query = em.createNativeQuery(nativeQuery);

        List<Object[]> results = query.getResultList();

        List<ConcertResponse> concertResponse = results.stream()
                .map(result -> ConcertResponse.builder()
                        .id((String) result[0])
                        .eventName((String) result[1])
                        .schedule((String) result[2])
                        .venue((String) result[3])
                        .concertCode((String) result[4])
                        .build())
                .collect(Collectors.toList());

        return concertResponse;
    }

    @Override
    public ConcertResponse getByIdConcert(String id) {
        String nativeQuery = "SELECT id, event_name, schedule, venue, concert_code FROM m_concert WHERE id = ?";
        Query query = em.createNativeQuery(nativeQuery)
                .setParameter(1, id);

        List<Object[]> results = query.getResultList();

        if (results.isEmpty()) {
            return null;
        }

        if (results.size() > 1){
            System.out.println("error");
        }

        Object[] result = results.get(0);

        return ConcertResponse.builder()
                .id((String) result[0])
                .eventName((String) result[1])
                .schedule((String) result[2])
                .venue((String) result[3])
                .concertCode((String) result[4])
                .build();
    }

    private ConcertResponse getConcertResponse(Concert concert) {
        return ConcertResponse.builder()
                .id(concert.getId())
                .eventName(concert.getEventName())
                .schedule(concert.getSchedule())
                .venue(concert.getVenue())
                .concertCode(concert.getConcertCode())
                .build();
    }


}
