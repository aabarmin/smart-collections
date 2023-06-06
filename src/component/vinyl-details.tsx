import React, { useEffect, useState } from "react";
import { Vinyl, VinylSide } from "../model/vinyl";
import { Container } from "react-bootstrap";
import Subheader from "./subheader";
import Covers from "./covers";
import VinylTitle from "./vinyl-title";
import VinylArtist from "./vinyl-artist";
import { useParams } from "react-router-dom";
import { getSingle } from "../actions/library";
import Loader from "./loader";
import { getTracks } from "../actions/library";
import VinylSides from "./vinyl-sides";

export default function VinylDetails() {
    const params = useParams();
    const id = params.id ? parseInt(params.id) : 0;

    const [vinyl, setItem] = useState(null as Vinyl | null);
    const [sides, setSides] = useState([] as VinylSide[]);

    useEffect(() => {
        getSingle(id)
            .then(vinyl => {
                setItem(vinyl);
            });

        getTracks(id)
            .then(sides => {
                setSides(sides);
            });
    }, [id]);

    if (vinyl == null) {
        return <Loader />
    }

    return (
        <>
            <Subheader header={vinyl.title} />
            <Container>
                <Covers images={vinyl.images} editable={false} />
                <VinylTitle title={vinyl.title} />
                <VinylArtist artist={vinyl.artist} />
                <VinylSides sides={sides} editable={false} />
            </Container>
        </>
    )
}