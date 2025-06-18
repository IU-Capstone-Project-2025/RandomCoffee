'use client';

import { backButton, on, postEvent } from '@telegram-apps/sdk-react';
import { PropsWithChildren, useEffect } from 'react';
import { useRouter } from 'next/navigation';

export function Page({ children, back = true, primaryColoredBackground = false }: PropsWithChildren<{
  /**
   * True if it is allowed to go back from this page.
   * @default true
   */
  back?: boolean
  primaryColoredBackground?: boolean
}>) {
  const router = useRouter();

  useEffect(() => {
    if (back) {
      backButton.show();
    } else {
      backButton.hide();
    }
  }, [back]);

  useEffect(() => {
    return backButton.onClick(() => {
      router.back();
    });
  }, [router]);

  return <div>
    <div className="fixed -z-10 top-0 left-0 w-screen h-screen" style={{ backgroundColor: primaryColoredBackground ? 'var(--tg-theme-bg-color)' : 'var(--tg-theme-secondary-bg-color)' }}>

    </div>
    {children}
    </div>;
}