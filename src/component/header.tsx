import React from "react";
import { Container, Nav, Navbar } from "react-bootstrap";

export default function Header() {
    return (
        <Navbar sticky="top" bg="primary">
            <Container>
                <Navbar.Brand href="/library">Vinyl library</Navbar.Brand>
                <Navbar.Toggle aria-controls="main-navbar" />
                <Navbar.Collapse id="main-navbar">
                    <Nav className="me-auto">
                        <Nav.Link href="/">Home</Nav.Link>
                        <Nav.Link href="/library">Library</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}