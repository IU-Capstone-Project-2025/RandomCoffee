package iu.profileservice.controller;

import iu.profileservice.api.MatchHistoryApi;
import iu.profileservice.facade.matchhistory.MatchHistoryFacade;
import iu.profileservice.model.MatchHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MatchHistoryController implements MatchHistoryApi {

    private final MatchHistoryFacade matchHistoryFacade;

    @Override
    public ResponseEntity<Void> createMatch(MatchHistoryDto matchHistoryDto) {
        matchHistoryFacade.createMatch(matchHistoryDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<MatchHistoryDto>> getMatchesBetweenDates(OffsetDateTime startDate, OffsetDateTime endDate) {
        return ResponseEntity.ok(matchHistoryFacade.getMatchesBetweenDates(startDate, endDate));
    }
}
