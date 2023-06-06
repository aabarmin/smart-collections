import React from "react";

interface Props {
    title: string;
    editable: boolean;
}

export default function VinylTitle({
    title, 
    editable = false
}: Props) {
    if (!editable) {
        return <h2>{title}</h2>
    }

    return (
        <div>
            {title}
        </div>
    )
}