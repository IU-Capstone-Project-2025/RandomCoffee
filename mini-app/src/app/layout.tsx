import { useEffect, type PropsWithChildren } from 'react';
import type { Metadata } from 'next';

import { Root } from '@/components/Root/Root';

import '@telegram-apps/telegram-ui/dist/styles.css';
import 'normalize.css/normalize.css';
import './_assets/globals.css';
import { on, postEvent, retrieveLaunchParams, useSignal } from '@telegram-apps/sdk-react';
import ProfileProvider from '@/components/custom/_provider/ProfileProvider';

export default async function RootLayout({ children }: PropsWithChildren) {
  return (
    <html suppressHydrationWarning>
      <body>
        <Root>
          {/* <ProfileProvider> */}
            {children}
          {/* </ProfileProvider> */}
        </Root>
      </body>
    </html>
  );
}
