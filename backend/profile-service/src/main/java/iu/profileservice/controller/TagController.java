package iu.profileservice.controller;

import iu.profileservice.api.TagApi;
import iu.profileservice.facade.tag.TagFacade;
import iu.profileservice.model.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TagController implements TagApi {

    private final TagFacade tagFacade;

    @Override
    public ResponseEntity<Void> createTag(TagDto tagDto) {
        tagFacade.createTag(tagDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ResponseEntity<TagDto> getTag(String name) {
        return ResponseEntity.ok(tagFacade.getTag(name));
    }

    @Override
    public ResponseEntity<TagDto> deleteTag(String name) {
        return ResponseEntity.ok(tagFacade.deleteTag(name));
    }

    @Override
    public ResponseEntity<Void> updateTag(TagDto tagDto) {
        tagFacade.updateTag(tagDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<TagDto>> getTagsByPrefix(String prefix) {
        return ResponseEntity.ok(tagFacade.getTagsByPrefix(prefix));
    }
}
