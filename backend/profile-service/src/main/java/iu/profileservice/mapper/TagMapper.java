package iu.profileservice.mapper;

import iu.profileservice.entity.Tag;
import iu.profileservice.model.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto toDto(Tag tag);

    @Mapping(target = "uuid", ignore = true)
    Tag toEntity(TagDto tagDto);
}
