import { useInitDataRawStore } from "@/store/initDataStore";
import axios, {Axios, AxiosInstance, InternalAxiosRequestConfig} from "axios";

export default class AxiosFactory {
    // Store the base URL as a variable that can be updated at runtime
    private static _baseUrl: string | undefined = process.env.NEXT_PUBLIC_API_BASE_URL;

    public static setBaseUrl(baseUrl: string | undefined): void {
        this._baseUrl = baseUrl;
    }

    public static getBaseUrl(): string | undefined {
        return this._baseUrl;
    }

    public static createAxiosInstance(path: string): AxiosInstance {
        const axiosInstance = axios.create();
        
        // Apply interceptors
        axiosInstance.interceptors.request.use(async (config) => {
            // Get the current base URL on every request
            config.baseURL = `${this._baseUrl}${path}`;
            
            // Add authorization header
            let token = useInitDataRawStore.getState().initDataRaw;
            if (token) {
                config.headers["Authorization"] = token;
            }
            
            return config;
        });
        
        return axiosInstance;
    }
}