import React from "react";
import { Container, Nav, Navbar } from "react-bootstrap";

export default function Footer() {
    return (
        <Navbar bg="light" fixed="bottom">
            <Container>
                <Nav>
                    <Nav.Link>Profile</Nav.Link>
                </Nav>
            </Container>
        </Navbar>
    )
}