package iu.profileservice.controller;

import iu.profileservice.api.MatchHistoryApi;
import iu.profileservice.facade.matchhistory.MatchHistoryFacade;
import iu.profileservice.model.MatchHistoryDto;
import iu.profileservice.model.ProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MatchHistoryController implements MatchHistoryApi {

    private final MatchHistoryFacade matchHistoryFacade;

    @Override
    public ResponseEntity<Void> createMatch(MatchHistoryDto matchHistoryDto) {
        log.info("POST /match-history");
        matchHistoryFacade.createMatch(matchHistoryDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ResponseEntity<List<MatchHistoryDto>> getMatchesBetweenDates(OffsetDateTime startDate, OffsetDateTime endDate) {
        log.info("GET /match-history");
        return ResponseEntity.ok(matchHistoryFacade.getMatchesBetweenDates(startDate, endDate));
    }

    @Override
    public ResponseEntity<ProfileDto> getMatchProfile(Long peerId) {
        log.info("GET /match-history/last-match");
        return ResponseEntity.ok(matchHistoryFacade.getMatch(peerId));
    }
}
