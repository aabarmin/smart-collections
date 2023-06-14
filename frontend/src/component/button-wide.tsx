import React from "react"
import { Button } from "react-bootstrap";

interface ButtonProps {
    onClick: () => void,
    title: string,
    variant?: string
}

const WideButton: React.FC<ButtonProps> = ({
    onClick,
    title,
    variant = "outline-primary"
}) => {
    return (
        <div className="d-grid gap-2">
            <Button
                onClick={onClick}
                variant={variant}
                size="lg">
                {title}
            </Button>
        </div>
    )
}

export default WideButton;