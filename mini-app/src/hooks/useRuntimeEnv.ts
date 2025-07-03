import { useState, useEffect } from 'react';

interface RuntimeEnv {
  API_BASE_URL?: string;
  // Add other environment variables as needed
  [key: string]: string | undefined;
}

/**
 * Custom hook to fetch and use runtime environment variables in client components
 */
export function useRuntimeEnv() {
  const [env, setEnv] = useState<RuntimeEnv>({});
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    const fetchEnv = async () => {
      try {
        const response = await fetch('/api/env');
        if (!response.ok) {
          throw new Error(`Failed to fetch environment variables: ${response.status}`);
        }
        const data = await response.json();
        setEnv(data);
      } catch (err) {
        setError(err instanceof Error ? err : new Error(String(err)));
      } finally {
        setLoading(false);
      }
    };

    fetchEnv();
  }, []);

  return { env, loading, error };
} 