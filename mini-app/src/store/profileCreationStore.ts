import { create } from 'zustand';

interface ProfileState {
  firstName: string;
  lastName: string;
  bio: string;
  setFirstName: (firstName: string) => void;
  setLastName: (lastName: string) => void;
  setBio: (bio: string) => void;
  reset: () => void;
}

export const useProfileCreationStore = create<ProfileState>()(
    (set) => ({
      firstName: '',
      lastName: '',
      bio: '',
      setFirstName: (firstName) => set({ firstName }),
      setLastName: (lastName) => set({ lastName }),
      setBio: (bio) => set({ bio }),
      reset: () => set({ firstName: '', lastName: '', bio: '' }),
    })
); 