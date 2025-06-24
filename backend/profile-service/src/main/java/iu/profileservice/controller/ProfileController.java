package iu.profileservice.controller;

import iu.profileservice.api.ProfileApi;
import iu.profileservice.facade.profile.ProfileFacade;
import iu.profileservice.model.ProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProfileController implements ProfileApi {

    private final ProfileFacade profileFacade;

    @Override
    public ResponseEntity<Void> createProfile(ProfileDto profileDto) {
        profileFacade.createProfile(profileDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ResponseEntity<ProfileDto> getProfile(Long peerId) {
        return ResponseEntity.ok(profileFacade.getProfile(peerId));
    }

    @Override
    public ResponseEntity<ProfileDto> deleteProfile(Long peerId) {
        return ResponseEntity.ok(profileFacade.deleteProfile(peerId));
    }

    @Override
    public ResponseEntity<Void> updateProfile(ProfileDto profileDto) {
        profileFacade.updateProfile(profileDto);
        return ResponseEntity.ok().build();
    }
}
