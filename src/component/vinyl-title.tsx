import { Col, Form, Row } from "react-bootstrap";
import ButtonEdit from "./button-edit";
import { useCallback, useState } from "react";
import ButtonSubmit from "./button-submit";
import ButtonCancel from "./button-cancel";
import { SubmitHandler, useForm } from "react-hook-form";

interface Props {
    title: string;
    onUpdate: (value: string) => void
}

type Inputs = {
    value: string; 
}

export default function VinylTitle({
    title, 
    onUpdate
}: Props) {
    const [editable, setEditable] = useState(false);
    const toggleEdit = useCallback(() => {
        setEditable(!editable);
    }, [editable]);
    const {register, handleSubmit} = useForm<Inputs>({
        defaultValues: {
            value: title
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
                    <h2>{title}</h2>
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