package iu.profileservice.controller;

import iu.profileservice.api.MatchmakingApi;
import iu.profileservice.facade.matchmaking.MatchmakingFacade;
import iu.profileservice.model.MatchHistoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MatchmakingController implements MatchmakingApi {

    private final MatchmakingFacade matchmakingFacade;

    @Override
    public ResponseEntity<List<MatchHistoryDto>> matchmaking() {
        log.info("GET /matchmaking");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(matchmakingFacade.matchmaking());
    }
}
