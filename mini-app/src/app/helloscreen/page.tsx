import { Page } from "@/components/Page";
import { Button, LargeTitle, Placeholder, Text, Title } from "@telegram-apps/telegram-ui";
import Image from "next/image";
import { useEffect } from "react";
import { useRouter } from "next/navigation";

export default function HelloScreen() {
    const router = useRouter();

    return (
        <Page back={false} primaryColoredBackground={true}>
            <div className="flex flex-col items-center justify-center h-screen" style={{ backgroundColor: 'var(--tg-theme-bg-color)' }}>
                <Placeholder
                    action={<Button size="l" stretched onClick={() => router.push('/profile/create/step1')}>Create a profile</Button>}
                    description="RandomCoffee is a platform for finding new friends and making new connections."
                    header="Welcome to RandomCoffee!"
                >
                    <Image
                        src="/stickers/duck/circles.png"
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