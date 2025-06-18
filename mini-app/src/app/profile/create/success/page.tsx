"use client";

import { Page } from "@/components/Page";
import { Button, LargeTitle, Placeholder, Text, Title } from "@telegram-apps/telegram-ui";
import Image from "next/image";
import { useEffect } from "react";
import { useRouter } from "next/navigation";

export default function ProfileCreatedPage() {
    const router = useRouter();

    return (
        <Page back={false} primaryColoredBackground={false}>
            <div className="flex flex-col items-center justify-center h-screen" style={{ backgroundColor: 'var(--tg-theme-secondary-bg-color)' }}>
                <Placeholder
                    action={<Button size="l" stretched onClick={() => router.push('/')}>To the app!</Button>}
                    description="Congratulations! Your profile has been created successfully. Now you can proceed to the app."
                    header="Profile created!"
                >
                    <Image
                        src="/stickers/duck/congrats.png"
                        alt="Waving Duck"
                        width={200}
                        height={200}
                        unoptimized
                    />
                </Placeholder>
            </div>
        </Page>
    )
}