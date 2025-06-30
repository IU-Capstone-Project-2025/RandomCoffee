"use client";

import { useSignal, initDataRaw as _initDataRaw } from "@telegram-apps/sdk-react";
import { useEffect } from "react";
import { create } from "zustand";
import { useProfileStore } from "@/store/profileStore";
import { useInitDataRawStore } from "@/store/initDataStore";
import ProfileService from "@/api/profile/service/ProfileService";
import { AxiosError } from "axios";

export default function ProfileProvider({ children }: { children: React.ReactNode }) {
    const initDataRaw = useSignal(_initDataRaw);
    const { setInitDataRaw } = useInitDataRawStore();
    const { setProfile, setProfileLoaded } = useProfileStore();

    useEffect(() => {
        if (initDataRaw) {
            setInitDataRaw(initDataRaw);
            ProfileService.getProfile().then((response) => {
                if (response) {
                    setProfile(response);
                }
            }).catch((error) => {
                if (error instanceof AxiosError) {
                    if (error.response?.status === 404) {
                        return;
                    }
                }
                console.error(error);
                // TODO: show error to user
            }).finally(() => {
                setProfileLoaded(true);
            });
        }
    }, [initDataRaw, setInitDataRaw]);

    return children;
}