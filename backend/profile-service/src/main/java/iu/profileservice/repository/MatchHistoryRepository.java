package iu.profileservice.repository;

import iu.profileservice.entity.MatchHistory;
import iu.profileservice.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MatchHistoryRepository extends JpaRepository<MatchHistory, UUID> {

    List<MatchHistory> findAllByProfile1AndProfile2(Profile profile1, Profile profile2);

    List<MatchHistory> findAllByTimestampBetween(OffsetDateTime startDate, OffsetDateTime endDate);
}
