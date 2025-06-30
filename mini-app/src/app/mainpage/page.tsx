"use client";

import { Page } from "@/components/Page";
import { Avatar, Tabbar, Text, Title } from "@telegram-apps/telegram-ui";
import { HomeIcon, UserIcon } from "lucide-react";
import MatchCard from "./_components/matchcard/MatchCard";
import MeetPoll from "./_components/meet-poll/MeetPoll";
import { initDataRaw as _initDataRaw, postEvent, retrieveLaunchParams, RetrieveLPResult, useSignal } from "@telegram-apps/sdk-react";
import { useEffect, useState } from "react";
import { init } from "@/core/init";
import TopScreenWhitespace from "@/components/custom/layout/TopScreenWhitespace";
import { useProfileStore } from "@/store/profileStore";
import MatchCardContainer from "./_components/matchcard/MatchCardContainer";

const subtitles = [
    "Welcome to the RandomCoffee",
    "The most random coffee is here",
    "Wanna a new cup?",
    "Now with alternative milk!",
    "New friends, new coffee",
    "New cup every week!",
    "Shuffling cups since 2025"
]

export default function MainPage() {
    const { profile } = useProfileStore();
    
    return (
        <Page back={false}>
            <TopScreenWhitespace />
            <div className="mb-20 p-4 flex flex-col gap-4">
                <div>
                    <Title weight="1" level="1" className="">
                        Hello, {profile?.name}!
                    </Title>
                    <Title weight="1" level="3" className="mb-2 -mt-1" style={{
                        color: 'var(--tg-theme-hint-color)'
                    }}>
                        {subtitles[Math.floor(Math.random() * subtitles.length)]}
                    </Title>
                </div>
                <MatchCardContainer />
            </div>
            <Tabbar className="pb-5">
                <Tabbar.Item
                    text="Home"
                    selected={true}
                >
                    <HomeIcon />
                </Tabbar.Item>
                <Tabbar.Item
                    text="Profile"
                >
                    <UserIcon />
                </Tabbar.Item>
            </Tabbar>
        </Page>
    )
}