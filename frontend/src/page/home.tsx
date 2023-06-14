import { useCallback } from "react";
import { Container } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import WideButton from "../component/button-wide";
import Header from "../component/header";

export default function Home() {
    const navigate = useNavigate();
    const onGoLibrary = useCallback(() => {
        navigate("/library")
    }, [navigate]);

    return (
        <>
            <Header />
            <Container style={{ paddingTop: "16px" }}>
                <WideButton
                    title="To the library"
                    onClick={onGoLibrary} />
            </Container>
        </>
    );
}