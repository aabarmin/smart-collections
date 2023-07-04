import React from "react";
import { Card } from "react-bootstrap";
import { VinylListItem } from "../model/vinyl";
import { getImagePath } from "../actions/library";

interface VinylItemProps {
    vinyl: VinylListItem, 
    onClick: (id: number) => void
}

const VinylItem: React.FC<VinylItemProps> = ({
    vinyl, onClick
}) => {
    let vinylCover = null;
    if (vinyl.vinyl_images.length > 0) {
        const vinylImage = vinyl.vinyl_images[0];
        vinylCover = (<Card.Img variant="top" src={getImagePath(vinylImage)} />)
    }

    return (
        <Card onClick={() => onClick(vinyl.vinyl_id)} >
            {vinylCover}
            <Card.Body>
                <Card.Title>{vinyl.vinyl_title}</Card.Title>
                <Card.Subtitle>{vinyl.vinyl_artist}</Card.Subtitle>
            </Card.Body>
        </Card>
    )
}

export default VinylItem;