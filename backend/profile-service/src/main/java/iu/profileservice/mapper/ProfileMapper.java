package iu.profileservice.mapper;

import iu.profileservice.entity.Profile;
import iu.profileservice.entity.Tag;
import iu.profileservice.model.ProfileDto;
import iu.profileservice.model.ProfileRequest;
import iu.profileservice.service.tag.TagService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ProfileMapper {

    @Autowired
    protected TagService tagService;

    @Mapping(target = "tags", expression = "java(toDtoTags(profile.getTags()))")
    public abstract ProfileDto toDto(Profile profile);

    @Mapping(target = "tags", expression = "java(tagService.findAllByNamesIn(profileDto.getTags()))")
    public abstract Profile toEntity(ProfileDto profileDto);

    @Mapping(target = "tags", expression = "java(toDtoTags(profile.getTags()))")
    public abstract ProfileRequest toRequest(Profile profile);

    protected Set<String> toDtoTags(Set<Tag> tags) {
        return tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
    }
}
