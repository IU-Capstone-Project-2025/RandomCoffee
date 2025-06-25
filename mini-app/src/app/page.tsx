'use client';

import HelloScreen from './helloscreen/page';
import MainPage from './mainpage/page';
import { useProfileStore } from '@/store/profileStore';

export default function Home() {
//   const { profile, profileLoaded } = useProfileStore();

//   if (!profileLoaded) {
//     return <div>Loading...</div>;
//   }

//   if (!profile) {
//     return <HelloScreen />
//   }

  return (
    <MainPage />
  );
}
