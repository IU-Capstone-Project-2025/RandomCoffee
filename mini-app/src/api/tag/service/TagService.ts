import AxiosFactory from "@/api/AxiosFactory";
import PromiseCache from "@/api/PromiseCache";
import { TagDTO } from "../dto/TagDTO";

export default class TagService {
    private static axios = AxiosFactory.createAxiosInstance("/tag")

    static getTags(prefix: string) : Promise<TagDTO[]> {
        return PromiseCache.getCachedPromise("TagService::getTags(" + prefix + ")", () => {
            return new Promise((resolve, reject) => {
                this.axios.get('', {
                    params: {
                        prefix: prefix,
                    }
                })
                    .then((response) => {
                        resolve(response.data as TagDTO[]);
                    })
                    .catch((error) => {
                        console.error(error)
                        reject(error.message);
                    });
            });
        })
    }
}