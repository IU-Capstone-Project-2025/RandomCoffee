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
     * Finds all match histories by the two specified profiles
     *
     * @param profile1 Profile 1
     * @param profile2 Profile 2
     * @return a list of match histories involving the two specified profiles
     */
    List<MatchHistory> findAllByProfile1AndProfile2(Profile profile1, Profile profile2);
}
