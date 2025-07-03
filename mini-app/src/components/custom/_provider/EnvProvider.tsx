'use client';

import React, { createContext, useContext, useEffect, useState, ReactNode } from 'react';
import AxiosFactory from '@/api/AxiosFactory';

interface EnvContextType {
  apiBaseUrl?: string;
  // Add other environment variables as needed
  isLoading: boolean;
  error: Error | null;
}

const EnvContext = createContext<EnvContextType>({
  apiBaseUrl: undefined,
  isLoading: true,
  error: null
});

export const useEnv = () => useContext(EnvContext);

interface EnvProviderProps {
  children: ReactNode;
  fallback?: ReactNode;
}

export const EnvProvider: React.FC<EnvProviderProps> = ({ children, fallback }) => {
  const [apiBaseUrl, setApiBaseUrl] = useState<string | undefined>(AxiosFactory.getBaseUrl());
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    const fetchEnv = async () => {
      try {
        // Use native fetch to avoid circular dependency with AxiosFactory
        const response = await fetch('/api/env');
        if (!response.ok) {
          throw new Error(`Failed to fetch environment variables: ${response.status}`);
        }
        const data = await response.json();
        
        // Update the API base URL
        if (data.API_BASE_URL) {
          setApiBaseUrl(data.API_BASE_URL);
          AxiosFactory.setBaseUrl(data.API_BASE_URL);
        }
        
        // Add other environment variables as needed
      } catch (err) {
        setError(err instanceof Error ? err : new Error(String(err)));
        console.error('Failed to load environment variables:', err);
      } finally {
        setIsLoading(false);
      }
    };

    fetchEnv();
  }, []);

  const value = {
    apiBaseUrl,
    isLoading,
    error
  };

  // Only render children after environment variables are loaded
  if (isLoading) {
    return fallback ? <>{fallback}</> : <div>Loading...</div>;
  }

  // If there's an error, you might want to show an error state or retry option
  if (error) {
    console.error('Failed to load environment variables:', error);
    // You can decide whether to render children anyway or show an error
    // For critical env vars, you might want to block rendering
    return <div>Error loading environment configuration: {error.message}</div>;
  }

  return <EnvContext.Provider value={value}>{children}</EnvContext.Provider>;
}; 