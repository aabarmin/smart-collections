import { VinylSide } from "../model/vinyl";
import { Accordion } from "react-bootstrap";
import VinylTrack from "./vinyl-track";
import { useCallback } from "react";
import { updateTrack } from "../actions/library";
import useToasts from "../actions/toasts";

interface Props {
    sides: VinylSide[]
}

export default function VinylSides({
    sides
} : Props) {
    const opened = sides.map(side => side.id)
        .map(side => side.toString());

    const {showToast} = useToasts();

    const onUpdateTrack = useCallback((trackId: number, title: string) => {
        updateTrack(trackId, title).then(() => {
            showToast("Track updated")
        });
    }, []);

    return (
        <Accordion defaultActiveKey={opened}>
            {sides.map(side => (
                <Accordion.Item eventKey={side.id.toString()} key={side.id}>
                    <Accordion.Header>{side.title}</Accordion.Header>
                    <Accordion.Body>
                        {side.tracks.map(track => (
                            <VinylTrack track={track.title} key={track.id} 
                                onUpdate={(value) => onUpdateTrack(track.id, value)} />
                        ))}
                    </Accordion.Body>
                </Accordion.Item>
            ))}
        </Accordion>
    )
}