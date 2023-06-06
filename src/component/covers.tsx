import React from "react";
import { Carousel } from "react-bootstrap";

interface Props {
    editable: boolean;
    images: string[];
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
                        <img src={img} className="d-block w-100" alt="" />
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