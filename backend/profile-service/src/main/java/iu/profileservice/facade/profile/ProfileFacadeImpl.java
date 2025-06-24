package iu.profileservice.facade.profile;

import iu.profileservice.exception.ResourceNotFoundException;
import iu.profileservice.mapper.ProfileMapper;
import iu.profileservice.model.ProfileDto;
import iu.profileservice.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileFacadeImpl implements ProfileFacade {

    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    @Override
    public void createProfile(ProfileDto profileDto) {
        profileService.createProfile(profileMapper.toEntity(profileDto));
    }

    @Override
    public ProfileDto getProfile(Long peerId) {
        return profileMapper.toDto(profileService.findById(peerId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile is not present"))
        );
    }

    @Override
    public void updateProfile(ProfileDto profileDto) {
        profileService.updateProfile(profileMapper.toEntity(profileDto));
    }

    @Override
    public ProfileDto deleteProfile(Long peerId) {
        return profileMapper.toDto(profileService.deleteProfile(peerId));
    }
}
