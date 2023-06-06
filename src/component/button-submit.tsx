import React from "react";
import { CheckCircle } from "react-bootstrap-icons";

export default function ButtonSubmit() {
    const style = {
        border: "none", 
        background: "none", 
        padding: "0px", 
        margin: "0px"
    };

    return (
        <button type="submit" style={style} value="1">
            <CheckCircle />
        </button>
    )
}