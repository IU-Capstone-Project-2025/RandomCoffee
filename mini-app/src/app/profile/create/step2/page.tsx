"use client";

import { Page } from "@/components/Page";
import { init } from "@/core/init";
import { initDataState, useSignal } from "@telegram-apps/sdk-react";
import { on, postEvent } from '@telegram-apps/sdk';
import { Button, LargeTitle, Placeholder, Text, Title, Input, List, Section, IconContainer, Cell, Steps, Caption, Subheadline, Textarea, Chip } from "@telegram-apps/telegram-ui";
import { SectionFooter } from "@telegram-apps/telegram-ui/dist/components/Blocks/Section/components/SectionFooter/SectionFooter";
import Image from "next/image";
import { useEffect, useState, useRef } from "react";
import { useProfileCreationStore } from "@/store/profileCreationStore";
import TagChip from "@/components/custom/tag/TagChip";
import { SectionHeader } from "@telegram-apps/telegram-ui/dist/components/Blocks/Section/components/SectionHeader/SectionHeader";
import { Icon16Cancel } from "@telegram-apps/telegram-ui/dist/icons/16/cancel";
import { PlusIcon, XIcon } from "lucide-react";
import { ModalHeader } from "@telegram-apps/telegram-ui/dist/components/Overlays/Modal/components/ModalHeader/ModalHeader";
import { ModalOverlay } from "@telegram-apps/telegram-ui/dist/components/Overlays/Modal/components/ModalOverlay/ModalOverlay";
import FancyModal from "@/components/custom/modal/FancyModal";
import { useRouter } from "next/navigation";
import TagService from "@/api/tag/service/TagService";

interface UserInfo {
    firstName: string;
    lastName: string;
    bio: string;
}

interface TagInfo {
    label: string;
    valid: boolean;
}

export default function Step2Tags() {
    const router = useRouter();
    const { firstName, lastName, bio } = useProfileCreationStore();
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [tags, setTags] = useState<TagInfo[]>([]);
    const [searchQuery, setSearchQuery] = useState("");
    const [cachedTags, setCachedTags] = useState<Set<string>>(new Set());
    const [suggestedTags, setSuggestedTags] = useState<string[]>([]);
    const [isLoading, setIsLoading] = useState(false);
    const searchTimeoutRef = useRef<NodeJS.Timeout | null>(null);

    const removeTag = (label: string) => {
        setTags(tags.filter((tag) => tag.label !== label));
    }

    const addTag = (label: string) => {
        setTags([...tags, { label, valid: true }]);
        setSearchQuery("");
        setIsModalOpen(false);
    }

    const searchTags = async (query: string) => {
        if (query.length < 2) {
            setSuggestedTags([]);
            return;
        }
        
        setIsLoading(true);
        try {
            const res = await TagService.getTags(query);
            
            if (res) {
                // Extract tag names from the API response
                const newTags = res.map(tag => tag.name);
                
                // Filter out tags that are already in the cachedTags set
                const uniqueNewTags = newTags.filter(name => !cachedTags.has(name));
                
                // Update the cachedTags set with the new tags
                setCachedTags(prev => new Set([...prev, ...uniqueNewTags]));
                
                // Update the suggestedTags array with all tags matching the current query
                setSuggestedTags(newTags);
            }
        } catch (error) {
            console.error("Error fetching tags:", error);
        } finally {
            setIsLoading(false);
        }
    }

    // Add debounced search effect
    useEffect(() => {
        if (searchQuery.length > 1) {
            // Clear any existing timeout
            if (searchTimeoutRef.current) {
                clearTimeout(searchTimeoutRef.current);
            }
            
            // Set a new timeout
            searchTimeoutRef.current = setTimeout(() => {
                searchTags(searchQuery);
            }, 500);
        } else {
            setSuggestedTags([]);
        }
        
        // Cleanup function to clear the timeout when component unmounts or searchQuery changes
        return () => {
            if (searchTimeoutRef.current) {
                clearTimeout(searchTimeoutRef.current);
            }
        };
    }, [searchQuery]);

    useEffect(() => {
        if (isModalOpen) {
            postEvent('web_app_setup_main_button', {
                is_visible: false,
            })
        } else {
            if (tags.length < 5) {
                postEvent('web_app_setup_main_button', {
                    is_visible: true,
                    is_active: false,
                    text: "Select at least 5 tags",
                    has_shine_effect: false,
                    text_color: "#F5F5F5",
                    color: "#6B7280"
                })
            } else {
                postEvent('web_app_setup_main_button', {
                    is_visible: true,
                    is_active: true,
                    text: "Submit",
                    has_shine_effect: true
                })
            }
        }
    }, [isModalOpen, tags]);

    useEffect(() => {
        postEvent('web_app_set_header_color', { color_key: 'secondary_bg_color' });

        return(() => {
            postEvent('web_app_setup_main_button', {
                is_visible: false
            })
        })
    }, [])

    useEffect(() => {
        const removeListener = on('main_button_pressed', payload => {
            router.push('/profile/create/success');
        })
        return () => removeListener();
    }, [firstName, lastName, bio]);

    return (
        <Page back={true}>
            <List className="mb-16 mt-16">
                <div className="py-10 gap-2 flex flex-col items-center justify-center">
                    <Image
                        src="/stickers/duck/tagsearch.png"
                        alt="Waving Duck"
                        width={200}
                        height={200}
                        unoptimized
                    />
                    <LargeTitle className="text-center" weight="1">Tags Selection</LargeTitle>
                    <Subheadline className="text-center">
                        Nice to meet you, {firstName}! Now select the tags that best describe you. It can be your tech stack - languages, frameworks, etc., or your field of work. You can change them later.
                    </Subheadline>
                </div>
                <div>
                    <SectionHeader className="">
                        Selected tags
                    </SectionHeader>
                    <div className="flex flex-wrap gap-2">
                        {tags.map((tag) => (
                            <TagChip key={tag.label} label={tag.label} removable={true} onRemove={() => removeTag(tag.label)} />
                        ))}
                        <Button before={<PlusIcon className="size-4 stroke-[3px]" />} mode="gray" size="s" onClick={() => setIsModalOpen(true)}>
                            Add tag
                        </Button>
                    </div>
                </div>
                <FancyModal
                    header={<ModalHeader>Add tag</ModalHeader>}
                    open={isModalOpen}
                    onOpenChange={(open) => setIsModalOpen(open)}
                    style={{
                        backgroundColor: 'var(--tg-theme-secondary-bg-color)'
                    }}
                >
                    <List className="mb-8">
                        <Section
                            header="Enter your tag"
                        >
                            <Input
                                placeholder="Java" 
                                value={searchQuery}
                                onChange={(e) => setSearchQuery(e.target.value)}
                            />
                        </Section>
                        <div>
                    <SectionHeader className="">
                        Suggested tags
                    </SectionHeader>
                    <div className="flex flex-wrap gap-2">
                        {searchQuery.length > 1 ? (
                            isLoading ? (
                                <Chip mode="outline">Loading...</Chip>
                            ) : suggestedTags.length > 0 ? (
                                suggestedTags
                                    .filter(tag => !tags.some(t => t.label === tag))
                                    .map(tag => (
                                        <Chip 
                                            mode="elevated" 
                                            key={tag}
                                            onClick={() => addTag(tag)}
                                        >
                                            {tag}
                                        </Chip>
                                    ))
                            ) : (
                                <Chip mode="outline">No matching tags found</Chip>
                            )
                        ) : (
                            <Chip mode="outline">Start typing to see suggestions</Chip>
                        )}
                    </div>
                </div>
                    </List>
                </FancyModal>
            </List>
        </Page>
    );
}