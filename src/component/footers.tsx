import React from "react";
import { Container, Nav, Navbar } from "react-bootstrap";
import DialogVinylCreate from "../dialog/dialog-vinyl-create";
import useCreateVinyl from "../actions/vinyl-create";

export default function Footer() {
    const {openDialog} = useCreateVinyl();

    return (
        <>
            <Navbar bg="light" fixed="bottom">
                <Container>
                    <Nav>
                        <Nav.Link onClick={openDialog}>Create Vinyl</Nav.Link>
                    </Nav>
                </Container>
            </Navbar>
            <DialogVinylCreate />
        </>
    )
}