export default class PromiseCache {
    private static promises: { [key: string]: Promise<any> | null } = {};

    static getCachedPromise<T>(key: string, promiseGenerator: () => Promise<T>): Promise<T> {
        if (this.promises[key]) {
            return this.promises[key] as Promise<T>;
        }

        const promise = promiseGenerator()
            .then((result) => {
                // Clear the cached promise once it resolves
                this.promises[key] = null;
                return result;
            })
            .catch((error) => {
                // Clear the cached promise if there's an error
                this.promises[key] = null;
                throw error;
            });

        this.promises[key] = promise;
        return promise;
    }
}