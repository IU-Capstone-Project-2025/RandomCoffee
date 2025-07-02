package iu.profileservice.integration;

import iu.profileservice.entity.Profile;
import iu.profileservice.entity.Tag;
import iu.profileservice.exception.ResourceNotFoundException;
import iu.profileservice.exception.ValidationException;
import iu.profileservice.facade.profile.ProfileFacade;
import iu.profileservice.facade.profile.ProfileFacadeImpl;
import iu.profileservice.mapper.ProfileMapper;
import iu.profileservice.model.ProfileDto;
import iu.profileservice.repository.ProfileRepository;
import iu.profileservice.service.profile.ProfileService;
import iu.profileservice.service.profile.ProfileServiceImpl;
import iu.profileservice.service.tag.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Integration tests that verify the interaction between multiple components
 * without requiring a full application context or database.
 */
@ExtendWith(MockitoExtension.class)
class ProfileServiceIntegrationTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private TagService tagService;

    @Mock
    private ProfileMapper profileMapper;

    private ProfileService profileService;
    private ProfileFacade profileFacade;

    private Profile testProfile;
    private ProfileDto testProfileDto;
    private Tag javaTag;
    private Tag springTag;

    @BeforeEach
    void setUp() {
        // Create real service and facade instances with mocked dependencies
        profileService = new ProfileServiceImpl(profileRepository);
        profileFacade = new ProfileFacadeImpl(profileService, profileMapper);

        // Set up test data
        javaTag = Tag.builder()
                .uuid(UUID.randomUUID())
                .name("Java")
                .build();

        springTag = Tag.builder()
                .uuid(UUID.randomUUID())
                .name("Spring")
                .build();

        testProfile = Profile.builder()
                .peerId(1L)
                .name("John")
                .surname("Doe")
                .bio("Software Developer")
                .tags(Set.of(javaTag, springTag))
                .build();

        testProfileDto = new ProfileDto();
        testProfileDto.setPeerId(1L);
        testProfileDto.setName("John");
        testProfileDto.setSurname("Doe");
        testProfileDto.setBio("Software Developer");
        testProfileDto.setTags(Set.of("Java", "Spring"));
    }

    @Test
    void createProfile_FullWorkflow_ShouldCreateProfileSuccessfully() {
        // Given
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());
        when(profileMapper.toEntity(testProfileDto)).thenReturn(testProfile);

        // When
        profileFacade.createProfile(testProfileDto, 1L);

        // Then
        verify(profileRepository).findById(1L);
        verify(profileMapper).toEntity(testProfileDto);
        verify(profileRepository).save(testProfile);
    }

    @Test
    void createProfile_WhenProfileExists_ShouldThrowValidationException() {
        // Given
        when(profileRepository.findById(1L)).thenReturn(Optional.of(testProfile));
        when(profileMapper.toEntity(testProfileDto)).thenReturn(testProfile);

        // When & Then
        assertThatThrownBy(() -> profileFacade.createProfile(testProfileDto, 1L))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Profile is present");

        verify(profileRepository).findById(1L);
        verify(profileMapper).toEntity(testProfileDto);
    }

    @Test
    void getProfile_FullWorkflow_ShouldReturnProfileDto() {
        // Given
        when(profileRepository.findById(1L)).thenReturn(Optional.of(testProfile));
        when(profileMapper.toDto(testProfile)).thenReturn(testProfileDto);

        // When
        ProfileDto result = profileFacade.getProfile(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getPeerId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("John");
        assertThat(result.getSurname()).isEqualTo("Doe");
        assertThat(result.getBio()).isEqualTo("Software Developer");
        assertThat(result.getTags()).containsExactlyInAnyOrder("Java", "Spring");

        verify(profileRepository).findById(1L);
        verify(profileMapper).toDto(testProfile);
    }

    @Test
    void getProfile_WhenNotExists_ShouldThrowResourceNotFoundException() {
        // Given
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> profileFacade.getProfile(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Profile is not present");

        verify(profileRepository).findById(1L);
    }

    @Test
    void updateProfile_FullWorkflow_ShouldUpdateSuccessfully() {
        // Given
        when(profileRepository.findById(1L)).thenReturn(Optional.of(testProfile));
        when(profileMapper.toEntity(testProfileDto)).thenReturn(testProfile);

        // When
        profileFacade.updateProfile(testProfileDto, 1L);

        // Then
        verify(profileRepository).findById(1L);
        verify(profileMapper).toEntity(testProfileDto);
        verify(profileRepository).save(testProfile);
    }

    @Test
    void updateProfile_WhenNotExists_ShouldThrowResourceNotFoundException() {
        // Given
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());
        when(profileMapper.toEntity(testProfileDto)).thenReturn(testProfile);

        // When & Then
        assertThatThrownBy(() -> profileFacade.updateProfile(testProfileDto, 1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Profile is not present");

        verify(profileRepository).findById(1L);
        verify(profileMapper).toEntity(testProfileDto);
    }

    @Test
    void deleteProfile_FullWorkflow_ShouldDeleteAndReturnProfileDto() {
        // Given
        when(profileRepository.findById(1L)).thenReturn(Optional.of(testProfile));
        when(profileMapper.toDto(testProfile)).thenReturn(testProfileDto);

        // When
        ProfileDto result = profileFacade.deleteProfile(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getPeerId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("John");
        assertThat(result.getSurname()).isEqualTo("Doe");
        assertThat(result.getBio()).isEqualTo("Software Developer");
        assertThat(result.getTags()).containsExactlyInAnyOrder("Java", "Spring");

        verify(profileRepository).findById(1L);
        verify(profileRepository).delete(testProfile);
        verify(profileMapper).toDto(testProfile);
    }

    @Test
    void facadeValidation_WithNullPeerIds_ShouldThrowValidationException() {
        // Given
        testProfileDto.setPeerId(null);

        // When & Then
        assertThatThrownBy(() -> profileFacade.createProfile(testProfileDto, null))
                .isInstanceOf(ValidationException.class)
                .hasMessage("peerId is not present");

        assertThatThrownBy(() -> profileFacade.updateProfile(testProfileDto, null))
                .isInstanceOf(ValidationException.class)
                .hasMessage("peerId is not present");
    }

    @Test
    void facadeValidation_WithPeerIdInParameter_ShouldSetInDto() {
        // Given
        testProfileDto.setPeerId(null);
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());
        when(profileMapper.toEntity(testProfileDto)).thenReturn(testProfile);

        // When
        profileFacade.createProfile(testProfileDto, 1L);

        // Then
        assertThat(testProfileDto.getPeerId()).isEqualTo(1L);
        verify(profileRepository).findById(1L);
        verify(profileMapper).toEntity(testProfileDto);
        verify(profileRepository).save(testProfile);
    }
} 