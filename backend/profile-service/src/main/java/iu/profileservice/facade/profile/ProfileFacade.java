package iu.profileservice.facade.profile;

import iu.profileservice.model.ProfileDto;

public interface ProfileFacade {

    /**
     * Create profile
     *
     * @param profileDto profile DTO
     * @throws iu.profileservice.exception.ValidationException profile is present
     */
    void createProfile(ProfileDto profileDto);

    /**
     * Get profile
     *
     * @param peerId peerId
     * @return DTO
     * @throws iu.profileservice.exception.ResourceNotFoundException profile is not present
     */
    ProfileDto getProfile(Long peerId);

    /**
     * Update profile
     *
     * @param profileDto profile DTO
     * @throws iu.profileservice.exception.ResourceNotFoundException profile is not present
     */
    void updateProfile(ProfileDto profileDto);

    /**
     * Delete profile
     *
     * @param peerId peerId
     * @return profile DTO
     * @throws iu.profileservice.exception.ResourceNotFoundException profile is not present
     */
    ProfileDto deleteProfile(Long peerId);
}
