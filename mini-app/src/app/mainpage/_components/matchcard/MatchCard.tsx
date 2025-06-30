"use client";

import { postEvent } from "@telegram-apps/sdk-react";
import { Avatar, Button, Card, Text, Title } from "@telegram-apps/telegram-ui";
import { MatchDTO } from "@/api/profile/dto/MatchDTO";
import MeetPoll from "../meet-poll/MeetPoll";

interface Props {
    match: MatchDTO;
}

export default function MatchCard(props: Props) {
    return (
        <div className="flex flex-col gap-4">
            <Card className="w-full p-4 pt-6 flex flex-col gap-8 items-center">
                <Text>
                    Your coffeemate for this week is
                </Text>
                <div className="flex flex-col items-center">
                    <Avatar
                        size={96}
                        src="https://avatars.githubusercontent.com/u/84640980?v=4"
                    />
                    <Title weight="1" className="mt-4">
                        {props.match.name} {props.match.surname}
                    </Title>
                    <Text weight="3">
                        @{props.match.peerId}
                    </Text>
                </div>
                <div className="flex flex-row gap-2 justify-stretch w-full">
                    <Button
                        mode="filled"
                        size="m"
                        className="flex-1"
                        onClick={() => {
                            postEvent('web_app_open_tg_link', {
                                path_full: `/${props.match.peerId}`
                            })
                        }}
                    >
                        Open chat
                    </Button>
                    <Button
                        mode="bezeled"
                        size="m"
                        className="flex-1"
                    >
                        View more
                    </Button>
                </div>
            </Card>
            <MeetPoll />
        </div>
    )
}