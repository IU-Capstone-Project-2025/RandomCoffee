package iu.profileservice.facade.matchhistory;

import iu.profileservice.model.MatchHistoryDto;
import iu.profileservice.model.ProfileDto;

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

    /**
     * Retrieves the mate for a given peer ID
     *
     * @param peerId the ID of the peer to retrieve the match for
     * @return the profile of the matched peer
     * @throws iu.profileservice.exception.ResourceNotFoundException if no match is found for the given peer ID
     * @throws iu.profileservice.exception.ResourceNotFoundException if the profile associated with the peer ID does not exist
     */
    ProfileDto getMatch(Long peerId);
}
