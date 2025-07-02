package iu.profileservice.service.profile;

import iu.profileservice.entity.Profile;
import iu.profileservice.entity.Tag;
import iu.profileservice.exception.ResourceNotFoundException;
import iu.profileservice.exception.ValidationException;
import iu.profileservice.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileServiceImpl profileService;

    private Profile testProfile;
    private Tag testTag;

    @BeforeEach
    void setUp() {
        testTag = Tag.builder()
                .uuid(UUID.randomUUID())
                .name("Java")
                .build();

        testProfile = Profile.builder()
                .peerId(1L)
                .name("John")
                .surname("Doe")
                .bio("Software Developer")
                .tags(Set.of(testTag))
                .build();
    }

    @Test
    void createProfile_WhenProfileDoesNotExist_ShouldCreateProfile() {
        // Given
        when(profileRepository.findById(testProfile.getPeerId())).thenReturn(Optional.empty());

        // When
        profileService.createProfile(testProfile);

        // Then
        verify(profileRepository).findById(testProfile.getPeerId());
        verify(profileRepository).save(testProfile);
    }

    @Test
    void createProfile_WhenProfileAlreadyExists_ShouldThrowValidationException() {
        // Given
        when(profileRepository.findById(testProfile.getPeerId())).thenReturn(Optional.of(testProfile));

        // When & Then
        assertThatThrownBy(() -> profileService.createProfile(testProfile))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Profile is present");

        verify(profileRepository).findById(testProfile.getPeerId());
        verify(profileRepository, never()).save(any());
    }

    @Test
    void findById_WhenProfileExists_ShouldReturnProfile() {
        // Given
        when(profileRepository.findById(1L)).thenReturn(Optional.of(testProfile));

        // When
        Optional<Profile> result = profileService.findById(1L);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testProfile);
        verify(profileRepository).findById(1L);
    }

    @Test
    void findById_WhenProfileDoesNotExist_ShouldReturnEmpty() {
        // Given
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<Profile> result = profileService.findById(1L);

        // Then
        assertThat(result).isEmpty();
        verify(profileRepository).findById(1L);
    }

    @Test
    void updateProfile_WhenProfileExists_ShouldUpdateProfile() {
        // Given
        when(profileRepository.findById(testProfile.getPeerId())).thenReturn(Optional.of(testProfile));

        // When
        profileService.updateProfile(testProfile);

        // Then
        verify(profileRepository).findById(testProfile.getPeerId());
        verify(profileRepository).save(testProfile);
    }

    @Test
    void updateProfile_WhenProfileDoesNotExist_ShouldThrowResourceNotFoundException() {
        // Given
        when(profileRepository.findById(testProfile.getPeerId())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> profileService.updateProfile(testProfile))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Profile is not present");

        verify(profileRepository).findById(testProfile.getPeerId());
        verify(profileRepository, never()).save(any());
    }

    @Test
    void deleteProfile_WhenProfileExists_ShouldDeleteAndReturnProfile() {
        // Given
        when(profileRepository.findById(1L)).thenReturn(Optional.of(testProfile));

        // When
        Profile result = profileService.deleteProfile(1L);

        // Then
        assertThat(result).isEqualTo(testProfile);
        verify(profileRepository).findById(1L);
        verify(profileRepository).delete(testProfile);
    }

    @Test
    void deleteProfile_WhenProfileDoesNotExist_ShouldThrowResourceNotFoundException() {
        // Given
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> profileService.deleteProfile(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Profile is not present");

        verify(profileRepository).findById(1L);
        verify(profileRepository, never()).delete(any());
    }

    @Test
    void findAll_ShouldReturnAllProfiles() {
        // Given
        Profile anotherProfile = Profile.builder()
                .peerId(2L)
                .name("Jane")
                .surname("Smith")
                .bio("Product Manager")
                .tags(Set.of())
                .build();
        
        List<Profile> expectedProfiles = List.of(testProfile, anotherProfile);
        when(profileRepository.findAll()).thenReturn(expectedProfiles);

        // When
        List<Profile> result = profileService.findAll();

        // Then
        assertThat(result)
                .hasSize(2)
                .containsExactlyElementsOf(expectedProfiles);
        verify(profileRepository).findAll();
    }

    @Test
    void findAll_WhenNoProfiles_ShouldReturnEmptyList() {
        // Given
        when(profileRepository.findAll()).thenReturn(List.of());

        // When
        List<Profile> result = profileService.findAll();

        // Then
        assertThat(result).isEmpty();
        verify(profileRepository).findAll();
    }
} 