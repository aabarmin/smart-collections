import React from "react";
import { Button, Form, InputGroup } from "react-bootstrap";

interface Props {
    id: string; 
    label: string;
    buttonLabel: string;
    onClick: () => void, 
    placeholder?: string;
    register: any
}

const FormTextWithButton: React.FC<Props> = ({
    id, 
    label, 
    buttonLabel, 
    onClick, 
    placeholder = label, 
    register
}) => {
    return (
        <InputGroup>
            <Form.Control 
                type="text"
                placeholder={placeholder} 
                {...register(id)} 
            />
            <Button 
                onClick={onClick}
                variant="outline-secondary">
                {buttonLabel}
            </Button>
        </InputGroup>
    )
}

export default FormTextWithButton;