package iu.profileservice.client;

import iu.profileservice.model.MatchHistoryDto;
import iu.profileservice.model.MatchmakingRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "matchmaking-client", url = "${ml.client.url}")
public interface MatchmakingClient {

    @PostMapping("/matchmaking")
    ResponseEntity<List<MatchHistoryDto>> matchmaking(@RequestBody MatchmakingRequest matchmakingRequest);
}
