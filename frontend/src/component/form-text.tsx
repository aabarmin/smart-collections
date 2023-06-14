import React from "react";
import { FloatingLabel, Form } from "react-bootstrap";

interface Props {
    id: string; 
    label: string;
    placeholder?: string;
    register: any
}

const FormText: React.FC<Props> = ({
    id, 
    label, 
    placeholder = label, 
    register
}) => {
    return (
        <FloatingLabel 
            className="mb-3"
            label={label}>

            <Form.Control 
                type="text"
                placeholder={placeholder} 
                {...register(id)} />
        </FloatingLabel>
    )
};

export default FormText;