import React from "react";
import { Container, Nav, Navbar } from "react-bootstrap";

interface Props {
    header: string
}

export default function Subheader({
    header
}: Props) {
    return (
        <Navbar bg="light">
            <Container>
                <Nav>
                    <Nav.Link href="/library">Back</Nav.Link>
                </Nav>
                <Navbar.Brand>{header}</Navbar.Brand>
            </Container>
        </Navbar>
    );
}