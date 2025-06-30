import { useEffect, useState } from "react";
import ProfileService from "@/api/profile/service/ProfileService";
import { MatchDTO } from "@/api/profile/dto/MatchDTO";
import { AxiosError } from "axios";
import MatchCard from "./MatchCard";
import MatchCardSkeleton from "./MatchCardSkeleton";
import MatchCardNoMatch from "./MatchCardNoMatch";

export default function MatchCardContainer() {
    const [match, setMatch] = useState<MatchDTO | null>(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        ProfileService.getLastMatch().then((response) => {
            setMatch(response);
        }).catch((error) => {
            if (error instanceof AxiosError) {
                if (error.response?.status === 404) {
                    return;
                }
            }
            console.error(error);
        }).finally(() => {
            setIsLoading(false);
        });
    }, []);

    if (isLoading) {
        return <MatchCardSkeleton />;
    }

    if (!match) {
        return <MatchCardNoMatch />;
    }

    return <MatchCard match={match} />;
}