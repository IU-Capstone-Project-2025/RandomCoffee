package iu.profileservice.facade.matchhistory;

import iu.profileservice.model.MatchHistoryDto;

import java.time.OffsetDateTime;
import java.util.List;

public interface MatchHistoryFacade {

    /**
     * Creates a new match history entry
     *
     * @param matchHistoryDto the match history DTO containing match details
     */
    void createMatch(MatchHistoryDto matchHistoryDto);

    /**
     * Retrieves a list of match history entries between the specified dates
     *
     * @param startDate date to start the search from
     * @param endDate   date to end the search at
     * @return a list of match history entries within the specified date range
     */
    List<MatchHistoryDto> getMatchesBetweenDates(OffsetDateTime startDate, OffsetDateTime endDate);
}
