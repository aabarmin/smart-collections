import { VinylSide } from "../model/vinyl";
import { useCallback } from "react";
import { updateTrack } from "../actions/library";
import useToasts from "../actions/toasts";
import TextEditable from "./text-editable";

interface Props {
    sides: VinylSide[]
}

export default function VinylSides({
    sides
} : Props) {
    const {showToast} = useToasts();
    const onUpdateTrack = useCallback((trackId: number, title: string) => {
        updateTrack(trackId, title).then(() => {
            showToast("Track updated")
        });
    }, [showToast]);

    return (
        <>
            {sides.map(side => (
                <section key={side.id}>
                    <h2>
                        <TextEditable text={side.title} onUpdate={() => {}} />
                    </h2>
                    <ul>
                        {side.tracks.map(track => (
                            <li key={track.id}>
                                <TextEditable 
                                    text={track.title} 
                                    onUpdate={(title) => onUpdateTrack(track.id, title)} />
                            </li>
                        ))}
                    </ul>
                </section>
            ))}
        </>
    )
}