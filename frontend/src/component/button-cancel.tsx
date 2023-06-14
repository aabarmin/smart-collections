import React from "react";
import { XCircle } from "react-bootstrap-icons";

interface Props {
    onClick: () => void
}

export default function ButtonCancel({onClick}: Props) {
    return <XCircle onClick={onClick} />
}