import type { NextConfig } from 'next';

const nextConfig: NextConfig = {
    output: "standalone",
    reactStrictMode: false,
    typescript: {
        ignoreBuildErrors: true,
    },
    allowedDevOrigins: ['127.0.0.1:3000'],
    devIndicators: {
        position: 'bottom-right'
    }
};

export default nextConfig;
