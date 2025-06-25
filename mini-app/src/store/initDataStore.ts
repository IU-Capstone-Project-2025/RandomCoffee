import { create } from "zustand";

interface InitDataRawStore {
    initDataRaw: string | null;
    setInitDataRaw: (initDataRaw: string) => void;
}

export const useInitDataRawStore = create<InitDataRawStore>((set) => ({
    initDataRaw: null,
    setInitDataRaw: (initDataRaw: string) => set({ initDataRaw }),
}))