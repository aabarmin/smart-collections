import React, { useCallback, useState } from "react";
import { Col, Form, Row } from "react-bootstrap";
import ButtonEdit from "./button-edit";
import { SubmitHandler, useForm } from "react-hook-form";
import ButtonSubmit from "./button-submit";
import ButtonCancel from "./button-cancel";

interface Props {
    track: string; 
    onUpdate: (value: string) => void
}

type Inputs = {
    value: string; 
}

export default function VinylTrack({
    track, 
    onUpdate
} : Props) {
    const [editable, setEditable] = useState(false);
    const toggleEdit = useCallback(() => {
        setEditable(!editable);
    }, [editable]);
    const {register, handleSubmit} = useForm<Inputs>({
        defaultValues: {
            value: track
        }
    }); 
    const onSubmit: SubmitHandler<Inputs> = (values) => {
        onUpdate(values.value);
        toggleEdit();
    }

    if (!editable) {
        return (
            <Row>
                <Col>{track}</Col>
                <Col xs={2}>
                    <ButtonEdit onClick={toggleEdit} />
                </Col>
            </Row>
        )
    }

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
        <Row>
            <Col>
                <Form.Control type="text" {...register("value", {required: true})} />
            </Col>
            <Col xs={1}>
                <ButtonSubmit />
            </Col>
            <Col xs={2}>
                <ButtonCancel onClick={toggleEdit} />
            </Col>
        </Row>
    </form>
    );
}