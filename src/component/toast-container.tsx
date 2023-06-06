import React, { useState } from "react";
import { Toast, ToastContainer } from "react-bootstrap";
import useToasts from "../actions/toasts";

export default function NotificationContainer() {
    const {toasts, removeFirst} = useToasts();

    return (
        <ToastContainer position="top-center">
            {toasts.map((toast, index) => (
                <Toast 
                    key={index}
                    onClose={() => removeFirst()}
                    delay={3000}
                    autohide>
                    <Toast.Header>Vinyl Collection</Toast.Header>
                    <Toast.Body>{toast}</Toast.Body>
                </Toast>
            ))}
        </ToastContainer>
    );
}