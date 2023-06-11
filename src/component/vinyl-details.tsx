import { useCallback, useEffect, useState } from "react";
import { Vinyl, VinylSide } from "../model/vinyl";
import { Container } from "react-bootstrap";
import Subheader from "./subheader";
import Covers from "./covers";
import { useParams } from "react-router-dom";
import { getSingle, updateArtist, updateTitle } from "../actions/library";
import Loader from "./loader";
import { getTracks } from "../actions/library";
import VinylSides from "./vinyl-sides";
import useToasts from "../actions/toasts";
import TextEditable from "./text-editable";

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

    const {showToast} = useToasts();

    const onUpdateArtist = useCallback((value: string) => {
        updateArtist(id, value).then(() => {
            showToast("Artist is updated")
        });
    }, [id, showToast]);

    const onUpdateTitle = useCallback((value: string) => {
        updateTitle(id, value).then(() => {
            showToast("Title updated")
        });
    }, [id, showToast]);

    if (vinyl == null) {
        return <Loader />
    }

    return (
        <>
            <Subheader header={vinyl.title} />
            <Container>
                <Covers images={vinyl.images} editable={false} />
                <h2>
                    <TextEditable 
                        onUpdate={onUpdateTitle}
                        text={vinyl.title} />
                </h2>
                <small>
                    <TextEditable 
                        onUpdate={onUpdateArtist}
                        text={vinyl.artist} />
                </small>
                <VinylSides sides={sides} />
            </Container>
        </>
    )
}
