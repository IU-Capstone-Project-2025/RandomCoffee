package iu.profileservice.service.matchhistory;

import iu.profileservice.entity.MatchHistory;
import iu.profileservice.entity.Profile;

import java.time.OffsetDateTime;
import java.util.List;

public interface MatchHistoryService {

    /**
     * Creates a new match history entry
     *
     * @param matchHistory the match history to create
     */
    void createMatch(MatchHistory matchHistory);

    /**
     * Finds all match histories between the specified timestamps
     *
     * @param startDate date to start from
     * @param endDate   date to end at
     * @return a list of match histories within the specified date range
     */
    List<MatchHistory> findAllByTimestampBetween(OffsetDateTime startDate, OffsetDateTime endDate);

    /**
     * Finds the last match history involving the specified profile, either as profile1 or profile2
     *
     * @param profile Profile to search for matches involving either profile
     * @return the last match history entry for the specified profile, ordered by timestamp descending
     * @throws iu.profileservice.exception.ResourceNotFoundException if no match history is found for the profile
     */
    MatchHistory findLastByProfileOrderByTimestampDesc(Profile profile);
}
