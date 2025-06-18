import { Chip } from "@telegram-apps/telegram-ui";
import { Icon16Cancel } from "@telegram-apps/telegram-ui/dist/icons/16/cancel";
import { XIcon } from "lucide-react";

interface Props {
    label: string;
    removable: boolean;
    onRemove?: () => void;
}

export default function TagChip({ label, removable, onRemove }: Props) {
    return (
        <Chip mode="elevated" after={removable ? <XIcon onClick={onRemove} className="size-4 stroke-[3px]" /> : undefined}>
            {label}
        </Chip>
    )
}