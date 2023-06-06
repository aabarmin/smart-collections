import { Col, Form, Row } from "react-bootstrap";
import ButtonEdit from "./button-edit";
import { useCallback, useState } from "react";
import ButtonSubmit from "./button-submit";
import ButtonCancel from "./button-cancel";

interface Props {
    title: string;
    onUpdate: (value: string) => void
}

export default function VinylTitle({
    title
}: Props) {
    const [editable, setEditable] = useState(false);
    const toggleEdit = useCallback(() => {
        setEditable(!editable);
    }, [editable]);

    if (!editable) {
        return (
            <Row>
                <Col>
                    <h2>{title}</h2>
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
                <ButtonSubmit />
            </Col>
            <Col xs={2}>
                <ButtonCancel onClick={toggleEdit} />
            </Col>
        </Row>
    )
}