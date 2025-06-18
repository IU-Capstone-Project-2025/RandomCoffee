'use client';

import { useEffect, type PropsWithChildren } from 'react';
import {
  miniApp,
  on,
  postEvent,
  useLaunchParams,
  useSignal,
} from '@telegram-apps/sdk-react';
import { TonConnectUIProvider } from '@tonconnect/ui-react';
import { AppRoot } from '@telegram-apps/telegram-ui';

import { ErrorBoundary } from '@/components/ErrorBoundary';
import { ErrorPage } from '@/components/ErrorPage';
import { useDidMount } from '@/hooks/useDidMount';

import './styles.css';

function RootInner({ children }: PropsWithChildren) {
  const lp = useLaunchParams();

  useEffect(() => {
    const removeListener = on('theme_changed', (payload) => {
      postEvent('web_app_set_header_color', { color: payload.theme_params.bg_color! })
    })
    return () => removeListener();
  }, [])

  useEffect(() => {
    if (lp.tgWebAppPlatform === 'ios' || lp.tgWebAppPlatform === 'android') {
      postEvent('web_app_request_fullscreen')
      postEvent('web_app_setup_swipe_behavior', {allow_vertical_swipe: false})
      postEvent('web_app_request_theme')
    }
  }, [lp.tgWebAppPlatform])

  const isDark = useSignal(miniApp.isDark);

  return (
    <TonConnectUIProvider manifestUrl="/tonconnect-manifest.json">
      <AppRoot
        appearance={isDark ? 'dark' : 'light'}
        platform={
          'ios'
        }
      >
        {children}
      </AppRoot>
    </TonConnectUIProvider>
  );
}

export function Root(props: PropsWithChildren) {
  // Unfortunately, Telegram Mini Apps does not allow us to use all features of
  // the Server Side Rendering. That's why we are showing loader on the server
  // side.
  const didMount = useDidMount();

  return didMount ? (
    <ErrorBoundary fallback={ErrorPage}>
      <RootInner {...props} />
    </ErrorBoundary>
  ) : (
    <div className="root__loading dark:bg-black dark:text-white">Loading...</div>
  );
}
