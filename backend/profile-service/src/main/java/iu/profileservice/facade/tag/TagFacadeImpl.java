package iu.profileservice.facade.tag;

import iu.profileservice.exception.ResourceNotFoundException;
import iu.profileservice.mapper.TagMapper;
import iu.profileservice.model.TagDto;
import iu.profileservice.service.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TagFacadeImpl implements TagFacade {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @Override
    public void createTag(TagDto tagDto) {
        tagService.createTag(tagMapper.toEntity(tagDto));
    }

    @Override
    public TagDto getTag(String name) {
        return tagMapper.toDto(tagService.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Tag is not present"))
        );
    }

    @Override
    public void updateTag(TagDto tagDto) {
        tagService.updateTag(tagMapper.toEntity(tagDto));
    }

    @Override
    public TagDto deleteTag(String name) {
        return tagMapper.toDto(tagService.deleteTagByName(name));
    }

    @Override
    public List<TagDto> getTagsByPrefix(String prefix) {
        if (prefix == null) {
            prefix = "";
        }

        return tagService.getTagsByPrefix(prefix).stream()
                .map(tagMapper::toDto)
                .toList();
    }
}
