package iu.profileservice.facade.matchmaking;

import iu.profileservice.model.MatchHistoryDto;

import java.util.List;

public interface MatchmakingFacade {

    List<MatchHistoryDto> matchmaking();
}
