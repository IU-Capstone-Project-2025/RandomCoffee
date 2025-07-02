import { useInitDataRawStore } from "@/store/initDataStore";
import axios, {Axios, AxiosInstance, InternalAxiosRequestConfig} from "axios";

export default class AxiosFactory {
    static readonly BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;

    public static createAxiosInstance(baseUrl: string): AxiosInstance {
        const axiosInstance = axios.create({
            baseURL: this.BASE_URL + baseUrl
        });
        axiosInstance.interceptors.request.use(this.requestInterceptor)
        return axiosInstance;
    }

    private static async requestInterceptor(config: InternalAxiosRequestConfig) {
        let token = useInitDataRawStore.getState().initDataRaw;
        if (token) {
            config.headers["Authorization"] = token;
        }
        return config;
    }
}