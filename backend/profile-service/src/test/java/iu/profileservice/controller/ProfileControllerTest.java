package iu.profileservice.controller;

import iu.profileservice.facade.profile.ProfileFacade;
import iu.profileservice.model.ProfileDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {

    @Mock
    private ProfileFacade profileFacade;

    @InjectMocks
    private ProfileController profileController;

    private ProfileDto testProfileDto;
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
    }

    @Test
    void createProfile_WithValidRequest_ShouldReturnCreatedStatus() {
        // Given
        doNothing().when(profileFacade).createProfile(testProfileDto, peerId);

        // When
        ResponseEntity<Void> response = profileController.createProfile(testProfileDto, peerId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNull();
        verify(profileFacade).createProfile(testProfileDto, peerId);
    }

    @Test
    void getProfile_WithValidPeerId_ShouldReturnOkWithProfileDto() {
        // Given
        when(profileFacade.getProfile(peerId)).thenReturn(testProfileDto);

        // When
        ResponseEntity<ProfileDto> response = profileController.getProfile(peerId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(testProfileDto);
        verify(profileFacade).getProfile(peerId);
    }

    @Test
    void updateProfile_WithValidRequest_ShouldReturnOkStatus() {
        // Given
        doNothing().when(profileFacade).updateProfile(testProfileDto, peerId);

        // When
        ResponseEntity<Void> response = profileController.updateProfile(testProfileDto, peerId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
        verify(profileFacade).updateProfile(testProfileDto, peerId);
    }

    @Test
    void deleteProfile_WithValidPeerId_ShouldReturnOkWithDeletedProfile() {
        // Given
        when(profileFacade.deleteProfile(peerId)).thenReturn(testProfileDto);

        // When
        ResponseEntity<ProfileDto> response = profileController.deleteProfile(peerId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(testProfileDto);
        verify(profileFacade).deleteProfile(peerId);
    }

    @Test
    void createProfile_WithNullProfileDto_ShouldStillCallFacade() {
        // Given
        doNothing().when(profileFacade).createProfile(null, peerId);

        // When
        ResponseEntity<Void> response = profileController.createProfile(null, peerId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(profileFacade).createProfile(null, peerId);
    }

    @Test
    void createProfile_WithNullPeerId_ShouldStillCallFacade() {
        // Given
        doNothing().when(profileFacade).createProfile(testProfileDto, null);

        // When
        ResponseEntity<Void> response = profileController.createProfile(testProfileDto, null);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(profileFacade).createProfile(testProfileDto, null);
    }

    @Test
    void getProfile_WithNullPeerId_ShouldStillCallFacade() {
        // Given
        ProfileDto nullPeerIdResult = new ProfileDto();
        when(profileFacade.getProfile(null)).thenReturn(nullPeerIdResult);

        // When
        ResponseEntity<ProfileDto> response = profileController.getProfile(null);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(nullPeerIdResult);
        verify(profileFacade).getProfile(null);
    }

    @Test
    void updateProfile_WithNullParameters_ShouldStillCallFacade() {
        // Given
        doNothing().when(profileFacade).updateProfile(null, null);

        // When
        ResponseEntity<Void> response = profileController.updateProfile(null, null);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(profileFacade).updateProfile(null, null);
    }

    @Test
    void deleteProfile_WithNullPeerId_ShouldStillCallFacade() {
        // Given
        ProfileDto deletedProfile = new ProfileDto();
        when(profileFacade.deleteProfile(null)).thenReturn(deletedProfile);

        // When
        ResponseEntity<ProfileDto> response = profileController.deleteProfile(null);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(deletedProfile);
        verify(profileFacade).deleteProfile(null);
    }
} 