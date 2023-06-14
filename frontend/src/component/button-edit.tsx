import React from "react";
import { Pencil } from "react-bootstrap-icons";

interface Props {
    onClick: () => void
}

export default function ButtonEdit({
    onClick
} : Props) {
    return (
        <Pencil onClick={onClick} />
    )
}