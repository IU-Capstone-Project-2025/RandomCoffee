package iu.profileservice.service.profile;

import iu.profileservice.entity.Profile;
import iu.profileservice.exception.ResourceNotFoundException;
import iu.profileservice.exception.ValidationException;
import iu.profileservice.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public void updateProfile(Profile profile) {
        if (findById(profile.getPeerId()).isEmpty()) {
            throw new ResourceNotFoundException("Profile is not present");
        }
        profileRepository.save(profile);
    }

    @Override
    public Profile deleteProfile(Long peerId) {
        Profile profile = findById(peerId).orElseThrow(() -> new ResourceNotFoundException("Profile is not present"));
        profileRepository.delete(profile);
        return profile;
    }

    @Override
    public void createProfile(Profile profile) {
        if (findById(profile.getPeerId()).isPresent()) {
            throw new ValidationException("Profile is present");
        }
        profileRepository.save(profile);
    }

    @Override
    public Optional<Profile> findById(Long peerId) {
        return profileRepository.findById(peerId);
    }
}
