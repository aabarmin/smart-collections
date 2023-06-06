import { Col, Form, Row } from "react-bootstrap";
import ButtonEdit from "./button-edit";
import { useCallback, useState } from "react";
import ButtonSave from "./button-save";
import ButtonCancel from "./button-cancel";

interface Props {
    artist: string; 
}

export default function AlbumArtist({
    artist
}: Props) {
    const [editable, setEditable] = useState(false);
    const toggleEdit = useCallback(() => {
        setEditable(!editable);
    }, [editable]);

    if (!editable) {
        return (
            <Row>
                <Col>
                    <small>by {artist}</small>
                </Col>
                <Col xs={2}>
                    <ButtonEdit onClick={toggleEdit} />
                </Col>
            </Row>
        )
    }

    return (
        <Row>
            <Col>
                <Form.Control type="text" />
            </Col>
            <Col xs={1}>
                <ButtonSave />
            </Col>
            <Col xs={2}>
                <ButtonCancel onClick={toggleEdit} />
            </Col>
        </Row>
    )
}