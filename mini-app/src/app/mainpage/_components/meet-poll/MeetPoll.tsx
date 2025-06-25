"use client";

import { Banner, Button, ButtonCell, Card, Cell, Divider, Section, Subheadline, Text } from "@telegram-apps/telegram-ui";
import { CheckIcon, CircleCheckIcon, CircleXIcon, CoffeeIcon, MailCheckIcon, MailboxIcon, XIcon } from "lucide-react";
import { Fragment } from "react";

export default function MeetPoll() {
    return (
        <div className="w-full flex flex-col gap-4">
            <Card className="p-4 w-full">
                <div>
                    <Text weight="1">
                        Meeting survey
                    </Text>
                    <Subheadline level="2">
                        Were you able to contact Anastasia?
                    </Subheadline>
                </div>
                <div className="flex flex-row overflow-hidden rounded-full mt-4">
                    <Button mode="gray" className="grow !rounded-none flex flex-row items-center">
                        <div className="flex flex-row items-center gap-1">
                            <CheckIcon />Yes
                        </div>
                    </Button>
                    <div className="flex-none w-px py-1.5" style={{backgroundColor: 'var(--tgui--plain_background)'}}>
                        <div className="w-full h-full" style={{backgroundColor: 'var(--tg-theme-subtitle-text-color)'}}></div>
                    </div>
                    <Button mode="gray" className="grow !rounded-none flex flex-row items-center">
                        <div className="flex flex-row items-center gap-1">
                            <XIcon />No
                        </div>
                    </Button>
                </div>
            </Card>
        </div>
    )
}