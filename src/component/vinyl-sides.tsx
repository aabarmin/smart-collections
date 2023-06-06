import { VinylSide } from "../model/vinyl";
import { Accordion } from "react-bootstrap";
import VinylTrack from "./vinyl-track";

interface Props {
    sides: VinylSide[]; 
    editable: boolean;
}

export default function VinylSides({
    sides, 
    editable
} : Props) {
    if (!editable) {
        const opened = sides.map(side => side.id)
            .map(side => side.toString());
        
        return (
            <Accordion defaultActiveKey={opened}>
                {sides.map(side => (
                    <Accordion.Item eventKey={side.id.toString()} key={side.id}>
                        <Accordion.Header>{side.title}</Accordion.Header>
                        <Accordion.Body>
                            {side.tracks.map(track => (
                                <VinylTrack track={track.title} key={track.id} editable={false} />
                            ))}
                        </Accordion.Body>
                    </Accordion.Item>
                ))}
            </Accordion>
        )
    }

    return (
        <ul>
            <li>track</li>
        </ul>
    )
}