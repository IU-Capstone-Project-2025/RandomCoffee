package iu.profileservice.facade.matchhistory;

import iu.profileservice.mapper.MatchHistoryMapper;
import iu.profileservice.model.MatchHistoryDto;
import iu.profileservice.service.matchhistory.MatchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchHistoryFacadeImpl implements MatchHistoryFacade {

    private final MatchHistoryService matchHistoryService;
    private final MatchHistoryMapper matchHistoryMapper;

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
}
