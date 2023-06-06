import React from "react";

interface Props {
    artist: string; 
    editable: boolean;
}

export default function AlbumArtist({
    artist, 
    editable
}: Props) {
    if (!editable) {
        return <small>by {artist}</small>
    }

    return (
        <div>
            Editable
        </div>
    )
}