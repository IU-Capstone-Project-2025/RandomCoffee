import { NextResponse } from 'next/server';

export async function GET() {
  // Only expose environment variables that should be available to the client
  // Add any runtime environment variables you need here
  const env = {
    API_BASE_URL: process.env.API_BASE_URL,
    // Add other runtime environment variables here
  };

  return NextResponse.json(env);
} 