package iu.profileservice.controller;

import iu.profileservice.api.TagApi;
import iu.profileservice.facade.tag.TagFacade;
import iu.profileservice.model.TagDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TagController implements TagApi {

    private final TagFacade tagFacade;

    @Override
    public ResponseEntity<Void> createTag(TagDto tagDto) {
        log.info("POST /tag");
        tagFacade.createTag(tagDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ResponseEntity<TagDto> getTag(String name) {
        log.info("GET /tag/{name}");
        return ResponseEntity.ok(tagFacade.getTag(name));
    }

    @Override
    public ResponseEntity<TagDto> deleteTag(String name) {
        log.info("DELETE /tag/{name}");
        return ResponseEntity.ok(tagFacade.deleteTag(name));
    }

    // TODO add tag name to parameters
    @Override
    public ResponseEntity<Void> updateTag(TagDto tagDto) {
        log.info("PATCH /tag");
        tagFacade.updateTag(tagDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<TagDto>> getTagsByPrefix(String prefix) {
        log.info("GET /tag");
        return ResponseEntity.ok(tagFacade.getTagsByPrefix(prefix));
    }
}
