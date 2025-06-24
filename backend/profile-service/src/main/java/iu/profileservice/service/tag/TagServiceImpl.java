package iu.profileservice.service.tag;

import iu.profileservice.entity.Tag;
import iu.profileservice.exception.ResourceNotFoundException;
import iu.profileservice.exception.ValidationException;
import iu.profileservice.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public void createTag(Tag tag) {
        if (findByName(tag.getName()).isPresent()) {
            throw new ValidationException("Tag is present");
        }
        tagRepository.save(tag);
    }

    @Override
    public Set<Tag> findAllByNamesIn(Collection<String> names) {
        return tagRepository.findAllByNameIn(names);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public void updateTag(Tag tag) {
        if (findByName(tag.getName()).isEmpty()) {
            throw new ResourceNotFoundException("Tag is not present");
        }
        tagRepository.save(tag);
    }

    @Override
    public Tag deleteTagByName(String name) {
        Tag tag = findByName(name).orElseThrow(() -> new ResourceNotFoundException("Tag is not present"));
        tagRepository.delete(tag);
        return tag;
    }

    @Override
    public Set<Tag> getTagsByPrefix(String prefix) {
        return tagRepository.findAllByNameContainsIgnoreCase(prefix);
    }
}
