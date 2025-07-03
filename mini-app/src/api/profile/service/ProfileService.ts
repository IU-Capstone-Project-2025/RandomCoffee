import AxiosFactory from "@/api/AxiosFactory";
import PromiseCache from "@/api/PromiseCache";
import { ProfileDTO } from "../dto/ProfileDTO";
import { MatchDTO } from "../dto/MatchDTO";

export default class ProfileService {
    private static profileAxios = AxiosFactory.createAxiosInstance("/profile");
    private static matchHistoryAxios = AxiosFactory.createAxiosInstance("/match-history");

    static getProfile() : Promise<ProfileDTO> {
        return PromiseCache.getCachedPromise("ProfileService::getProfile", () => {
            return new Promise((resolve, reject) => {
                this.profileAxios.get('')
                    .then((response) => {
                        resolve(response.data as ProfileDTO);
                    })
                    .catch((error) => {
                        reject(error);
                    });
            });
        })
    }

    static createProfile(profile: ProfileDTO): Promise<void> {
        return new Promise((resolve, reject) => {
            this.profileAxios.post('', profile)
                .then(() => {
                    resolve();
                })
                .catch((error) => {
                    reject(error);
                });
        });
    }

    static getLastMatch() : Promise<MatchDTO> {
        return new Promise((resolve, reject) => {
            this.matchHistoryAxios.get('/last-match')
                .then((response) => {
                    resolve(response.data as MatchDTO);
                })
                .catch((error) => {
                    reject(error);
                });
        });
    }
}