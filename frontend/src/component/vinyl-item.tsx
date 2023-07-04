import React from "react";
import { Card } from "react-bootstrap";
import { VinylListItem } from "../model/vinyl";

interface VinylItemProps {
    vinyl: VinylListItem, 
    onClick: (id: number) => void
}

const VinylItem: React.FC<VinylItemProps> = ({
    vinyl, onClick
}) => {
    return (
        <Card onClick={() => onClick(vinyl.vinyl_id)} >
            <Card.Img variant="top" src={vinyl.cover} />
            <Card.Body>
                <Card.Title>{vinyl.vinyl_title}</Card.Title>
                <Card.Subtitle>{vinyl.vinyl_artist}</Card.Subtitle>
            </Card.Body>
        </Card>
    )
}

export default VinylItem;