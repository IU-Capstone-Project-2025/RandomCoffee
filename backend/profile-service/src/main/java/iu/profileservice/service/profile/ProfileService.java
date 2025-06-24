package iu.profileservice.service.profile;

import iu.profileservice.entity.Profile;

import java.util.Optional;

public interface ProfileService {

    /**
     * Create profile
     *
     * @param profile profile entity
     * @throws iu.profileservice.exception.ValidationException profile is present
     */
    void createProfile(Profile profile);

    /**
     * Find entity by peerId
     *
     * @param peerId peerId
     * @return entity
     */
    Optional<Profile> findById(Long peerId);

    /**
     * Update profile
     *
     * @param profile profile entity
     * @throws iu.profileservice.exception.ResourceNotFoundException profile is not present
     */
    void updateProfile(Profile profile);

    /**
     * Delete profile
     *
     * @param peerId peerId
     * @return profile entity
     * @throws iu.profileservice.exception.ResourceNotFoundException profile is not present
     */
    Profile deleteProfile(Long peerId);
}
