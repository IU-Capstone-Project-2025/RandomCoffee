import { useLaunchParams } from "@telegram-apps/sdk-react";

export default function TopScreenWhitespace() {
    const lp = useLaunchParams();

    if (lp.tgWebAppPlatform === 'ios' || lp.tgWebAppPlatform === 'android') {
        return <div style={{ height: '6rem' }} />
    }

    return null;
}