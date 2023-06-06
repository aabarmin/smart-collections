import React from "react";

interface Props {
    track: string; 
    editable: false;
}

export default function VinylTrack({
    track, 
    editable
} : Props) {
    if (!editable) {
        return <p>{track}</p>
    }

    return <div>abc</div>
}