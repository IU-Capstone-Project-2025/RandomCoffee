package iu.profileservice.facade.matchmaking;

import iu.profileservice.client.MatchmakingClient;
import iu.profileservice.mapper.MatchHistoryMapper;
import iu.profileservice.mapper.ProfileMapper;
import iu.profileservice.model.MatchHistoryDto;
import iu.profileservice.model.MatchmakingRequest;
import iu.profileservice.service.matchhistory.MatchHistoryService;
import iu.profileservice.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MatchmakingFacadeImpl implements MatchmakingFacade {

    private final MatchmakingClient matchmakingClient;
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;
    private final MatchHistoryService matchHistoryService;
    private final MatchHistoryMapper matchHistoryMapper;

    @Transactional
    @Override
    public List<MatchHistoryDto> matchmaking() {
        return Objects.requireNonNull(matchmakingClient.matchmaking(
                        new MatchmakingRequest()
                                .usersData(profileService.findAll().stream()
                                        .map(profileMapper::toRequest)
                                        .toList())
                ).getBody()).stream()
                .map(matchHistoryMapper::toEntity)
                .map(matchHistoryService::createMatch)
                .map(matchHistoryMapper::toDto)
                .toList();
    }
}
