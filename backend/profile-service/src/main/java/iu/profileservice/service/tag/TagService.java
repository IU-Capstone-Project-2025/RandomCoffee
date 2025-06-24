package iu.profileservice.service.tag;

import iu.profileservice.entity.Tag;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface TagService {

    /**
     * Create tag
     *
     * @param tag tag entity
     * @throws iu.profileservice.exception.ValidationException tag is present
     */
    void createTag(Tag tag);

    /**
     * Find tags by tag names
     *
     * @param names tag names
     * @return tags
     */
    Set<Tag> findAllByNamesIn(Collection<String> names);


    /**
     * Find tag by name
     *
     * @param name tag name
     * @return entity
     */
    Optional<Tag> findByName(String name);

    /**
     * Update tag
     *
     * @param tag tag entity
     * @throws iu.profileservice.exception.ResourceNotFoundException tag is not present
     */
    void updateTag(Tag tag);

    /**
     * Delete tag by name
     *
     * @param name tag name
     * @return deleted tag
     * @throws iu.profileservice.exception.ResourceNotFoundException tag is not present
     */
    Tag deleteTagByName(String name);

    /**
     * Get tags by prefix
     *
     * @param prefix tag prefix
     * @return set of tags
     */
    Set<Tag> getTagsByPrefix(String prefix);
}
