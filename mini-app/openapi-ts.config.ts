import { defineConfig } from '@hey-api/openapi-ts';

export default defineConfig({
    input: 'gateway-openapi.yaml',
    output: 'src/api',
    plugins: ['@hey-api/client-axios'], 
});