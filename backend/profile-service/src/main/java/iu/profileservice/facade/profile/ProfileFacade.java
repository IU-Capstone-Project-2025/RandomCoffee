package iu.profileservice.facade.profile;

import iu.profileservice.model.ProfileDto;

public interface ProfileFacade {

    /**
     * Create profile
     *
     * @param profileDto profile DTO
     * @param peerId     peerId
     * @throws iu.profileservice.exception.ValidationException profile is present
     * @throws iu.profileservice.exception.ValidationException peerId is not present
     */
    void createProfile(ProfileDto profileDto, Long peerId);

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
     * @param peerId     peerId
     * @throws iu.profileservice.exception.ResourceNotFoundException profile is not present
     * @throws iu.profileservice.exception.ValidationException       peerId is not present
     */
    void updateProfile(ProfileDto profileDto, Long peerId);

    /**
     * Delete profile
     *
     * @param peerId peerId
     * @return profile DTO
     * @throws iu.profileservice.exception.ResourceNotFoundException profile is not present
     */
    ProfileDto deleteProfile(Long peerId);
}
