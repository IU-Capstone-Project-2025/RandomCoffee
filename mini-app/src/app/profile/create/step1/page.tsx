"use client";

import { Page } from "@/components/Page";
import { init } from "@/core/init";
import { initDataState, useSignal } from "@telegram-apps/sdk-react";
import { on, postEvent } from '@telegram-apps/sdk';
import { Button, LargeTitle, Placeholder, Text, Title, Input, List, Section, IconContainer, Cell, Steps, Caption, Subheadline, Textarea } from "@telegram-apps/telegram-ui";
import { SectionFooter } from "@telegram-apps/telegram-ui/dist/components/Blocks/Section/components/SectionFooter/SectionFooter";
import Image from "next/image";
import { useEffect, useState } from "react";
import { useProfileCreationStore } from "@/store/profileCreationStore";
import { useRouter } from "next/navigation";

interface UserInfo {
    firstName: string;
    lastName: string;
    bio: string;
}

export default function Step1Name() {
    const router = useRouter();
    const { firstName, lastName, bio, setFirstName, setLastName, setBio } = useProfileCreationStore();
    const initData = useSignal(initDataState);

    useEffect(() => {
        if (initData?.user && (!firstName || !lastName)) {
            setFirstName(initData.user.first_name || "");
            setLastName(initData.user.last_name || "");
        }
    }, [initData, setFirstName, setLastName]);

    useEffect(() => {
        if (firstName.length > 0 && lastName.length > 0 && bio.length > 0) {
            postEvent('web_app_setup_main_button', {
                is_visible: true,
                is_active: true,
                text: "Next step",
                has_shine_effect: true
            })
        } else {
            postEvent('web_app_setup_main_button', {
                is_visible: true,
                is_active: false,
                text: "Fill in all the fields",
                has_shine_effect: false,
                text_color: "#F5F5F5",
                color: "#6B7280"
            })
        }
    }, [firstName, lastName, bio]);

    useEffect(() => {
        const removeListener = on('main_button_pressed', payload => {
            console.log("Main button pressed", payload);
            router.push('/profile/create/step2');
        })
        return () => removeListener();
    }, [firstName, lastName, bio]);

    useEffect(() => {
        return(() => {
            postEvent('web_app_setup_main_button', {
                is_visible: false
            })
        })
    }, [])

    return (
        <Page back={true}>
            <List className="mb-16 mt-16">
                <div className="py-10 gap-2 flex flex-col items-center justify-center">
                    <Image
                        src="/stickers/duck/waving.png"
                        alt="Waving Duck"
                        width={200}
                        height={200}
                        unoptimized
                    />
                    <LargeTitle className="text-center" weight="1">Profile Creation</LargeTitle>
                    <Subheadline className="text-center">
                        We need a little bit of information from you to get started. Fill this form to create your RandomCoffee profile.
                    </Subheadline>
                </div>
                
                <Section
                    header="YOUR NAME"
                    footer="We've got your first and last name from your Telegram account. You can change it here."
                >
                    <div className="dark:bg-black">
                        <Input
                            value={firstName}
                            onChange={(e) => setFirstName(e.target.value)}
                            placeholder="First Name"
                        />
                        <Input
                            value={lastName}
                            onChange={(e) => setLastName(e.target.value)}
                            placeholder="Last Name"
                        />
                    </div>
                </Section>
                <Section
                    header="YOUR BIO"
                    footer="Tell us about yourself in a few words. This will be displayed on your profile."
                >
                    <Textarea
                        style={{
                            height: '120px',
                        }}
                        value={bio}
                        onChange={(e) => setBio(e.target.value)}
                        placeholder="Backend developer from Innopolis who loves spending weekends on a supboard and exploring the city on my bicycle. Always up for a coffee and tech talk!"
                    />
                </Section>
            </List>
        </Page>
    );
}