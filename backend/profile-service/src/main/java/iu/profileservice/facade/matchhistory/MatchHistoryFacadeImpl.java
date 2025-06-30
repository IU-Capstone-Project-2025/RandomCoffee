package iu.profileservice.facade.matchhistory;

import iu.profileservice.entity.MatchHistory;
import iu.profileservice.exception.ResourceNotFoundException;
import iu.profileservice.mapper.MatchHistoryMapper;
import iu.profileservice.mapper.ProfileMapper;
import iu.profileservice.model.MatchHistoryDto;
import iu.profileservice.model.ProfileDto;
import iu.profileservice.service.matchhistory.MatchHistoryService;
import iu.profileservice.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchHistoryFacadeImpl implements MatchHistoryFacade {

    private final MatchHistoryService matchHistoryService;
    private final MatchHistoryMapper matchHistoryMapper;
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    @Override
    public void createMatch(MatchHistoryDto matchHistoryDto) {
        matchHistoryService.createMatch(matchHistoryMapper.toEntity(matchHistoryDto));
    }

    @Override
    public List<MatchHistoryDto> getMatchesBetweenDates(OffsetDateTime startDate, OffsetDateTime endDate) {
        return matchHistoryService.findAllByTimestampBetween(startDate, endDate)
                .stream()
                .map(matchHistoryMapper::toDto)
                .toList();
    }

    @Override
    public ProfileDto getMatch(Long peerId) {
        MatchHistory matchHistory = matchHistoryService.findLastByProfileOrderByTimestampDesc(
                profileService.findById(peerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Profile is not present"))
        );

        if (peerId.equals(matchHistory.getProfile1().getPeerId())) {
            return profileMapper.toDto(matchHistory.getProfile2());
        } else if (peerId.equals(matchHistory.getProfile2().getPeerId())) {
            return profileMapper.toDto(matchHistory.getProfile1());
        }

        return null;
    }
}
