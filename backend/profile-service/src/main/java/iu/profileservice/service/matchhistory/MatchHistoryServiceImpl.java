package iu.profileservice.service.matchhistory;

import iu.profileservice.entity.MatchHistory;
import iu.profileservice.entity.Profile;
import iu.profileservice.repository.MatchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchHistoryServiceImpl implements MatchHistoryService {

    private final MatchHistoryRepository matchHistoryRepository;

    @Override
    public void createMatch(MatchHistory matchHistory) {
        matchHistoryRepository.save(matchHistory);
    }

    @Override
    public List<MatchHistory> findAllByTimestampBetween(OffsetDateTime startDate, OffsetDateTime endDate) {
        return matchHistoryRepository.findAllByTimestampBetween(startDate, endDate);
    }

    @Override
    public List<MatchHistory> findAllByProfile1AndProfile2(Profile profile1, Profile profile2) {
        return matchHistoryRepository.findAllByProfile1AndProfile2(profile1, profile2);
    }
}
