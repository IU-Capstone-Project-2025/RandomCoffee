package iu.profileservice.service.matchhistory;

import iu.profileservice.entity.MatchHistory;
import iu.profileservice.entity.Profile;
import iu.profileservice.exception.ResourceNotFoundException;
import iu.profileservice.repository.MatchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class MatchHistoryServiceImpl implements MatchHistoryService {

    private final MatchHistoryRepository matchHistoryRepository;

    @Override
    public MatchHistory createMatch(MatchHistory matchHistory) {
        return matchHistoryRepository.save(matchHistory);
    }

    @Override
    public List<MatchHistory> findAllByTimestampBetween(OffsetDateTime startDate, OffsetDateTime endDate) {
        return matchHistoryRepository.findAllByTimestampBetween(startDate, endDate);
    }

    @Override
    public MatchHistory findLastByProfileOrderByTimestampDesc(Profile profile) {
        try {
            return matchHistoryRepository.findAllByProfile1OrProfile2OrderByTimestampDesc(profile, profile).getFirst();
        } catch (NoSuchElementException exception) {
            throw new ResourceNotFoundException("Match history is not present", exception);
        }
    }
}
