package iu.profileservice.facade.profile;

import iu.profileservice.entity.Profile;
import iu.profileservice.entity.Tag;
import iu.profileservice.exception.ResourceNotFoundException;
import iu.profileservice.exception.ValidationException;
import iu.profileservice.mapper.ProfileMapper;
import iu.profileservice.model.ProfileDto;
import iu.profileservice.service.profile.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
class ProfileFacadeTest {

    @Mock
    private ProfileService profileService;

    @Mock
    private ProfileMapper profileMapper;

    @InjectMocks
    private ProfileFacadeImpl profileFacade;

    private ProfileDto testProfileDto;
    private Profile testProfile;
    private Long peerId;

    @BeforeEach
    void setUp() {
        peerId = 1L;
        
        testProfileDto = new ProfileDto();
        testProfileDto.setPeerId(peerId);
        testProfileDto.setName("John");
        testProfileDto.setSurname("Doe");
        testProfileDto.setBio("Software Developer");
        testProfileDto.setTags(Set.of("Java", "Spring"));

        Tag tag = Tag.builder()
                .uuid(UUID.randomUUID())
                .name("Java")
                .build();

        testProfile = Profile.builder()
                .peerId(peerId)
                .name("John")
                .surname("Doe")
                .bio("Software Developer")
                .tags(Set.of(tag))
                .build();
    }

    @Test
    void createProfile_WithValidProfileDtoAndPeerId_ShouldCreateProfile() {
        // Given
        when(profileMapper.toEntity(testProfileDto)).thenReturn(testProfile);

        // When
        profileFacade.createProfile(testProfileDto, peerId);

        // Then
        verify(profileMapper).toEntity(testProfileDto);
        verify(profileService).createProfile(testProfile);
        assertThat(testProfileDto.getPeerId()).isEqualTo(peerId);
    }

    @Test
    void createProfile_WithNullPeerIdInDto_ShouldSetPeerIdFromParameter() {
        // Given
        testProfileDto.setPeerId(null);
        when(profileMapper.toEntity(testProfileDto)).thenReturn(testProfile);

        // When
        profileFacade.createProfile(testProfileDto, peerId);

        // Then
        assertThat(testProfileDto.getPeerId()).isEqualTo(peerId);
        verify(profileMapper).toEntity(testProfileDto);
        verify(profileService).createProfile(testProfile);
    }

    @Test
    void createProfile_WithNullPeerIdInBothDtoAndParameter_ShouldThrowValidationException() {
        // Given
        testProfileDto.setPeerId(null);

        // When & Then
        assertThatThrownBy(() -> profileFacade.createProfile(testProfileDto, null))
                .isInstanceOf(ValidationException.class)
                .hasMessage("peerId is not present");

        verify(profileMapper, never()).toEntity(any());
        verify(profileService, never()).createProfile(any());
    }

    @Test
    void getProfile_WhenProfileExists_ShouldReturnProfileDto() {
        // Given
        when(profileService.findById(peerId)).thenReturn(Optional.of(testProfile));
        when(profileMapper.toDto(testProfile)).thenReturn(testProfileDto);

        // When
        ProfileDto result = profileFacade.getProfile(peerId);

        // Then
        assertThat(result).isEqualTo(testProfileDto);
        verify(profileService).findById(peerId);
        verify(profileMapper).toDto(testProfile);
    }

    @Test
    void getProfile_WhenProfileDoesNotExist_ShouldThrowResourceNotFoundException() {
        // Given
        when(profileService.findById(peerId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> profileFacade.getProfile(peerId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Profile is not present");

        verify(profileService).findById(peerId);
        verify(profileMapper, never()).toDto(any());
    }

    @Test
    void updateProfile_WithValidProfileDtoAndPeerId_ShouldUpdateProfile() {
        // Given
        when(profileMapper.toEntity(testProfileDto)).thenReturn(testProfile);

        // When
        profileFacade.updateProfile(testProfileDto, peerId);

        // Then
        verify(profileMapper).toEntity(testProfileDto);
        verify(profileService).updateProfile(testProfile);
        assertThat(testProfileDto.getPeerId()).isEqualTo(peerId);
    }

    @Test
    void updateProfile_WithNullPeerIdInDto_ShouldSetPeerIdFromParameter() {
        // Given
        testProfileDto.setPeerId(null);
        when(profileMapper.toEntity(testProfileDto)).thenReturn(testProfile);

        // When
        profileFacade.updateProfile(testProfileDto, peerId);

        // Then
        assertThat(testProfileDto.getPeerId()).isEqualTo(peerId);
        verify(profileMapper).toEntity(testProfileDto);
        verify(profileService).updateProfile(testProfile);
    }

    @Test
    void updateProfile_WithNullPeerIdInBothDtoAndParameter_ShouldThrowValidationException() {
        // Given
        testProfileDto.setPeerId(null);

        // When & Then
        assertThatThrownBy(() -> profileFacade.updateProfile(testProfileDto, null))
                .isInstanceOf(ValidationException.class)
                .hasMessage("peerId is not present");

        verify(profileMapper, never()).toEntity(any());
        verify(profileService, never()).updateProfile(any());
    }

    @Test
    void deleteProfile_WhenProfileExists_ShouldDeleteAndReturnProfileDto() {
        // Given
        when(profileService.deleteProfile(peerId)).thenReturn(testProfile);
        when(profileMapper.toDto(testProfile)).thenReturn(testProfileDto);

        // When
        ProfileDto result = profileFacade.deleteProfile(peerId);

        // Then
        assertThat(result).isEqualTo(testProfileDto);
        verify(profileService).deleteProfile(peerId);
        verify(profileMapper).toDto(testProfile);
    }

    @Test
    void deleteProfile_WhenProfileDoesNotExist_ShouldPropagateResourceNotFoundException() {
        // Given
        when(profileService.deleteProfile(peerId))
                .thenThrow(new ResourceNotFoundException("Profile is not present"));

        // When & Then
        assertThatThrownBy(() -> profileFacade.deleteProfile(peerId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Profile is not present");

        verify(profileService).deleteProfile(peerId);
        verify(profileMapper, never()).toDto(any());
    }
} 