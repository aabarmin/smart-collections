import React, { useCallback, useState } from "react";
import { Col, Form, Row } from "react-bootstrap";
import { useForm, SubmitHandler } from "react-hook-form";
import ButtonEdit from "./button-edit";
import ButtonSubmit from "./button-submit";
import ButtonCancel from "./button-cancel";

interface Props {
    text: string; 
    onUpdate: (value: string) => void
}

type Inputs = {
    value: string; 
}

export default function TextEditable({
    text, onUpdate
} : Props) {
    const [editable, setEditable] = useState(false);
    const toggleEdit = useCallback(() => {
        setEditable(!editable);
    }, [editable]);
    const {register, handleSubmit} = useForm<Inputs>({
        defaultValues: {
            value: text
        }
    }); 
    const onSubmit: SubmitHandler<Inputs> = (values) => {
        onUpdate(values.value);
        toggleEdit();
    }

    if (!editable) {
        return (
            <Row>
                <Col>
                    {text}
                </Col>
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
    )
}