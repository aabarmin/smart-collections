import React from "react";
import { Carousel } from "react-bootstrap";
import { VinylImage } from "../model/vinyl";
import { getImagePath } from "../actions/library";

interface Props {
    editable: boolean;
    images: VinylImage[];
}

export default function Covers({
    editable = false, 
    images = []
} : Props) {
    if (!editable) {
        return (
            <Carousel>
                {images.map((img, index) => (
                    <Carousel.Item key={index}>
                        <img src={getImagePath(img)} className="d-block w-100" alt="" />
                    </Carousel.Item>
                ))}
            </Carousel>
        )
    } 

    return (
        <div>
            Editable carousel
        </div>
    )
}