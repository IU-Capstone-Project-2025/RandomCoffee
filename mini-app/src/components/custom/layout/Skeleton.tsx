export default function Skeleton({ children, className }: { children: React.ReactNode, className?: string }) {
    return <div className={`bg-gray-200 rounded-md animate-pulse ${className}`}>
        <div className="opacity-0">
            {children}
        </div>
    </div>
}