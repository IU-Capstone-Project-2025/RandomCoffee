package iu.profileservice.facade.tag;

import iu.profileservice.model.TagDto;

import java.util.List;

public interface TagFacade {

    /**
     * Create tag
     *
     * @param tagDto tag DTO
     * @throws iu.profileservice.exception.ValidationException tag is present
     */
    void createTag(TagDto tagDto);

    /**
     * Get tag
     *
     * @param name tag name
     * @return DTO
     * @throws iu.profileservice.exception.ResourceNotFoundException tag is not present
     */
    TagDto getTag(String name);

    /**
     * Update tag
     *
     * @param tagDto tag DTO
     * @throws iu.profileservice.exception.ResourceNotFoundException tag is not present
     */
    void updateTag(TagDto tagDto);

    /**
     * Delete tag
     *
     * @param name tag name
     * @return tag DTO
     * @throws iu.profileservice.exception.ResourceNotFoundException tag is not present
     */
    TagDto deleteTag(String name);

    /**
     * Get tags by prefix
     *
     * @param prefix tag prefix
     * @return list of tag DTOs
     */
    List<TagDto> getTagsByPrefix(String prefix);
}
