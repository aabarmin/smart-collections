import React from "react";
import { Card } from "react-bootstrap";
import { Vinyl } from "../model/vinyl";

interface VinylItemProps {
    vinyl: Vinyl, 
    onClick: (id: number) => void
}

export default function VinylItem(props: VinylItemProps) {
    return (
        <Card onClick={() => props.onClick(props.vinyl.id)} >
            <Card.Img variant="top" src={props.vinyl.cover} />
            <Card.Body>
                <Card.Title>{props.vinyl.title}</Card.Title>
                <Card.Subtitle>{props.vinyl.artist}</Card.Subtitle>
            </Card.Body>
        </Card>
    )
}