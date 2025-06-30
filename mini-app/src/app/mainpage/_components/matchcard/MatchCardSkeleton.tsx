"use client";

import Skeleton from "@/components/custom/layout/Skeleton";
import { Avatar, Button, Card, Text, Title } from "@telegram-apps/telegram-ui";

export default function MatchCardSkeleton() {
    return (
        <Card className="w-full p-4 pt-6 flex flex-col gap-8 items-center">
            <Skeleton>
                <Text>
                    Your coffeemate for this week is
                </Text>
            </Skeleton>
            <div className="flex flex-col items-center">
                <Skeleton className="!rounded-full">
                    <Avatar
                        size={96}
                        src="https://avatars.githubusercontent.com/u/84640980?v=4"
                    />
                </Skeleton>
                <Skeleton className="mt-4">
                    <Title weight="1">
                        Name Surname
                    </Title>
                </Skeleton>
                <Skeleton className="">
                    <Text weight="3">
                        @cool_username
                    </Text>
                </Skeleton>
            </div>
            <div className="flex flex-row gap-2 justify-stretch w-full">
                <Button
                    mode="filled"
                    size="m"
                    className="flex-1"
                    disabled
                >
                    Open chat
                </Button>
                <Button
                    mode="bezeled"
                    size="m"
                    className="flex-1"
                    disabled
                >
                    View more
                </Button>
            </div>
        </Card>
    )
} 