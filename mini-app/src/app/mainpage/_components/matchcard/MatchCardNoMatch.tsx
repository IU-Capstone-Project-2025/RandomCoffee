"use client";

import { Avatar, Button, Card, Skeleton, Text, Title } from "@telegram-apps/telegram-ui";
import Image from "next/image";

export default function MatchCardNoMatch() {
    return (
        <Card className="w-full p-4 pt-6 flex flex-col items-center text-center">
            <Image
                src="/stickers/duck/dontknow.png"
                alt="Waving Duck"
                width={200}
                height={200}
                unoptimized
            />
            <Title weight="1" className="mt-4">
                No match this week!
            </Title>
            <Text weight="3" className="mt-2 mb-4 px-4">
                Sorry, but we couldn&apos;t find a coffemate for you this week. Come back next week to find a new one!
            </Text>
        </Card>
    )
} 