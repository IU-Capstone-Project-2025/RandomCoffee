import { ProfileDTO } from "@/api/profile/dto/ProfileDTO";
import { create } from "zustand";

interface ProfileState {
    profile: ProfileDTO | null;
    profileLoaded: boolean;
    setProfile: (profile: ProfileDTO) => void;
    setProfileLoaded: (profileLoaded: boolean) => void;
}

export const useProfileStore = create<ProfileState>()(
    (set) => ({
        profile: null,
        profileLoaded: false,
        setProfile: (profile) => set({ profile }),
        setProfileLoaded: (profileLoaded) => set({ profileLoaded }),
    })
)