package iu.profileservice.service.tag;

import iu.profileservice.entity.Tag;
import iu.profileservice.exception.ResourceNotFoundException;
import iu.profileservice.exception.ValidationException;
import iu.profileservice.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    private Tag testTag;
    private String tagName;

    @BeforeEach
    void setUp() {
        tagName = "Java";
        testTag = Tag.builder()
                .uuid(UUID.randomUUID())
                .name(tagName)
                .build();
    }

    @Test
    void createTag_WhenTagDoesNotExist_ShouldCreateTag() {
        // Given
        when(tagRepository.findByName(tagName)).thenReturn(Optional.empty());

        // When
        tagService.createTag(testTag);

        // Then
        verify(tagRepository).findByName(tagName);
        verify(tagRepository).save(testTag);
    }

    @Test
    void createTag_WhenTagAlreadyExists_ShouldThrowValidationException() {
        // Given
        when(tagRepository.findByName(tagName)).thenReturn(Optional.of(testTag));

        // When & Then
        assertThatThrownBy(() -> tagService.createTag(testTag))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Tag is present");

        verify(tagRepository).findByName(tagName);
        verify(tagRepository, never()).save(any());
    }

    @Test
    void findAllByNamesIn_WhenTagsExist_ShouldReturnTags() {
        // Given
        Tag springTag = Tag.builder()
                .uuid(UUID.randomUUID())
                .name("Spring")
                .build();
        
        Collection<String> tagNames = List.of("Java", "Spring");
        Set<Tag> expectedTags = Set.of(testTag, springTag);
        
        when(tagRepository.findAllByNameIn(tagNames)).thenReturn(expectedTags);

        // When
        Set<Tag> result = tagService.findAllByNamesIn(tagNames);

        // Then
        assertThat(result)
                .hasSize(2)
                .containsExactlyInAnyOrderElementsOf(expectedTags);
        verify(tagRepository).findAllByNameIn(tagNames);
    }

    @Test
    void findAllByNamesIn_WhenNoTagsExist_ShouldReturnEmptySet() {
        // Given
        Collection<String> tagNames = List.of("NonExistent");
        when(tagRepository.findAllByNameIn(tagNames)).thenReturn(Set.of());

        // When
        Set<Tag> result = tagService.findAllByNamesIn(tagNames);

        // Then
        assertThat(result).isEmpty();
        verify(tagRepository).findAllByNameIn(tagNames);
    }

    @Test
    void findByName_WhenTagExists_ShouldReturnTag() {
        // Given
        when(tagRepository.findByName(tagName)).thenReturn(Optional.of(testTag));

        // When
        Optional<Tag> result = tagService.findByName(tagName);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testTag);
        verify(tagRepository).findByName(tagName);
    }

    @Test
    void findByName_WhenTagDoesNotExist_ShouldReturnEmpty() {
        // Given
        when(tagRepository.findByName(tagName)).thenReturn(Optional.empty());

        // When
        Optional<Tag> result = tagService.findByName(tagName);

        // Then
        assertThat(result).isEmpty();
        verify(tagRepository).findByName(tagName);
    }

    @Test
    void updateTag_WhenTagExists_ShouldUpdateTag() {
        // Given
        when(tagRepository.findByName(testTag.getName())).thenReturn(Optional.of(testTag));

        // When
        tagService.updateTag(testTag);

        // Then
        verify(tagRepository).findByName(testTag.getName());
        verify(tagRepository).save(testTag);
    }

    @Test
    void updateTag_WhenTagDoesNotExist_ShouldThrowResourceNotFoundException() {
        // Given
        when(tagRepository.findByName(testTag.getName())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> tagService.updateTag(testTag))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Tag is not present");

        verify(tagRepository).findByName(testTag.getName());
        verify(tagRepository, never()).save(any());
    }

    @Test
    void deleteTagByName_WhenTagExists_ShouldDeleteAndReturnTag() {
        // Given
        when(tagRepository.findByName(tagName)).thenReturn(Optional.of(testTag));

        // When
        Tag result = tagService.deleteTagByName(tagName);

        // Then
        assertThat(result).isEqualTo(testTag);
        verify(tagRepository).findByName(tagName);
        verify(tagRepository).delete(testTag);
    }

    @Test
    void deleteTagByName_WhenTagDoesNotExist_ShouldThrowResourceNotFoundException() {
        // Given
        when(tagRepository.findByName(tagName)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> tagService.deleteTagByName(tagName))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Tag is not present");

        verify(tagRepository).findByName(tagName);
        verify(tagRepository, never()).delete(any());
    }

    @Test
    void getTagsByPrefix_WhenTagsExist_ShouldReturnMatchingTags() {
        // Given
        String prefix = "Ja";
        Tag javascriptTag = Tag.builder()
                .uuid(UUID.randomUUID())
                .name("JavaScript")
                .build();
        
        Set<Tag> expectedTags = Set.of(testTag, javascriptTag);
        when(tagRepository.findAllByNameContainsIgnoreCase(prefix)).thenReturn(expectedTags);

        // When
        Set<Tag> result = tagService.getTagsByPrefix(prefix);

        // Then
        assertThat(result)
                .hasSize(2)
                .containsExactlyInAnyOrderElementsOf(expectedTags);
        verify(tagRepository).findAllByNameContainsIgnoreCase(prefix);
    }

    @Test
    void getTagsByPrefix_WhenNoTagsMatch_ShouldReturnEmptySet() {
        // Given
        String prefix = "NonExistent";
        when(tagRepository.findAllByNameContainsIgnoreCase(prefix)).thenReturn(Set.of());

        // When
        Set<Tag> result = tagService.getTagsByPrefix(prefix);

        // Then
        assertThat(result).isEmpty();
        verify(tagRepository).findAllByNameContainsIgnoreCase(prefix);
    }

    @Test
    void getTagsByPrefix_WithEmptyPrefix_ShouldStillCallRepository() {
        // Given
        String emptyPrefix = "";
        when(tagRepository.findAllByNameContainsIgnoreCase(emptyPrefix)).thenReturn(Set.of(testTag));

        // When
        Set<Tag> result = tagService.getTagsByPrefix(emptyPrefix);

        // Then
        assertThat(result).containsExactly(testTag);
        verify(tagRepository).findAllByNameContainsIgnoreCase(emptyPrefix);
    }

    @Test
    void getTagsByPrefix_WithNullPrefix_ShouldStillCallRepository() {
        // Given
        when(tagRepository.findAllByNameContainsIgnoreCase(null)).thenReturn(Set.of());

        // When
        Set<Tag> result = tagService.getTagsByPrefix(null);

        // Then
        assertThat(result).isEmpty();
        verify(tagRepository).findAllByNameContainsIgnoreCase(null);
    }
} 