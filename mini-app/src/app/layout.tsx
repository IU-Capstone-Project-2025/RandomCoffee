import { useEffect, type PropsWithChildren } from 'react';
import type { Metadata } from 'next';

import { Root } from '@/components/Root/Root';

import '@telegram-apps/telegram-ui/dist/styles.css';
import 'normalize.css/normalize.css';
import './_assets/globals.css';
import { on, postEvent, retrieveLaunchParams, useSignal } from '@telegram-apps/sdk-react';
import ProfileProvider from '@/components/custom/_provider/ProfileProvider';
import { EnvProvider } from '@/components/custom/_provider/EnvProvider';

export default async function RootLayout({ children }: PropsWithChildren) {
  return (
    <html suppressHydrationWarning>
      <body>
        <Root>
          <EnvProvider
            fallback={
              <div style={{ 
                display: 'flex', 
                justifyContent: 'center', 
                alignItems: 'center', 
                height: '100vh',
                flexDirection: 'column',
                gap: '1rem'
              }}>
                <div>Loading...</div>
              </div>
            }
          >
            <ProfileProvider>
              {children}
            </ProfileProvider>
          </EnvProvider>
        </Root>
      </body>
    </html>
  );
}
