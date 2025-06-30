package iu.profileservice.mapper;

import iu.profileservice.entity.MatchHistory;
import iu.profileservice.model.MatchHistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MatchHistoryMapper {

    @Mapping(target = "peerId1", source = "matchHistory.profile1.peerId")
    @Mapping(target = "peerId2", source = "matchHistory.profile2.peerId")
    MatchHistoryDto toDto(MatchHistory matchHistory);

    @Mapping(target = "profile1.peerId", source = "matchHistoryDto.peerId1")
    @Mapping(target = "profile2.peerId", source = "matchHistoryDto.peerId2")
    @Mapping(target = "timestamp", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "uuid", ignore = true)
    MatchHistory toEntity(MatchHistoryDto matchHistoryDto);
}
