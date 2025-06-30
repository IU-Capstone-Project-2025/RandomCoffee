package iu.profileservice.facade.profile;

import iu.profileservice.exception.ResourceNotFoundException;
import iu.profileservice.exception.ValidationException;
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
    public void createProfile(ProfileDto profileDto, Long peerId) {
        validatePeerId(profileDto, peerId);
        profileService.createProfile(profileMapper.toEntity(profileDto));
    }

    @Override
    public ProfileDto getProfile(Long peerId) {
        return profileMapper.toDto(profileService.findById(peerId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile is not present"))
        );
    }

    @Override
    public void updateProfile(ProfileDto profileDto, Long peerId) {
        validatePeerId(profileDto, peerId);
        profileService.updateProfile(profileMapper.toEntity(profileDto));
    }

    @Override
    public ProfileDto deleteProfile(Long peerId) {
        return profileMapper.toDto(profileService.deleteProfile(peerId));
    }

    private void validatePeerId(ProfileDto profileDto, Long peerId) {
        if (profileDto.getPeerId() == null && peerId == null) {
            throw new ValidationException("peerId is not present");
        }
        if (profileDto.getPeerId() == null) {
            profileDto.setPeerId(peerId);
        }
    }
}
