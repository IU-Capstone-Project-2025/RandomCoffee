import AxiosFactory from "@/api/AxiosFactory";
import PromiseCache from "@/api/PromiseCache";
import { ProfileDTO } from "../dto/ProfileDTO";

export default class ProfileService {
    private static axios = AxiosFactory.createAxiosInstance("/profile")

    static getProfile() : Promise<ProfileDTO> {
        return PromiseCache.getCachedPromise("ProfileService::getProfile", () => {
            return new Promise((resolve, reject) => {
                this.axios.get('')
                    .then((response) => {
                        resolve(response.data as ProfileDTO);
                    })
                    .catch((error) => {
                        console.error(error)
                        reject(error.message);
                    });
            });
        })
    }
}