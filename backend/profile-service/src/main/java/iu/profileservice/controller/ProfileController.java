package iu.profileservice.controller;

import iu.profileservice.api.ProfileApi;
import iu.profileservice.facade.profile.ProfileFacade;
import iu.profileservice.model.ProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProfileController implements ProfileApi {

    private final ProfileFacade profileFacade;

    @Override
    public ResponseEntity<Void> createProfile(ProfileDto profileDto, Long peerId) {
        log.info("POST /profile");
        profileFacade.createProfile(profileDto, peerId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ResponseEntity<ProfileDto> getProfile(Long peerId) {
        log.info("GET /profile");
        return ResponseEntity.ok(profileFacade.getProfile(peerId));
    }

    @Override
    public ResponseEntity<ProfileDto> deleteProfile(Long peerId) {
        log.info("DELETE /profile");
        return ResponseEntity.ok(profileFacade.deleteProfile(peerId));
    }

    @Override
    public ResponseEntity<Void> updateProfile(ProfileDto profileDto, Long peerId) {
        log.info("PATCH /profile");
        profileFacade.updateProfile(profileDto, peerId);
        return ResponseEntity.ok().build();
    }
}
