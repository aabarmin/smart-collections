import Header from "../component/header";
import VinylList from "../component/vinyl-list";
import Footer from "../component/footers";
import { Navbar, Container, Nav } from "react-bootstrap";
import useCreateVinyl from "../actions/vinyl-create";
import { useGoogleLogin } from "@react-oauth/google";
import React, { useState } from "react";
import WideButton from "../component/button-wide";
import axios from "axios";

const LibraryContent: React.FC<any> = () => {
    const { openDialog } = useCreateVinyl();

    return (
        <>
            <Navbar bg="light">
                <Container>
                    <Nav>
                        <Nav.Item>
                            <Nav.Link onClick={openDialog}>
                                Add vinyl
                            </Nav.Link>
                        </Nav.Item>
                    </Nav>
                </Container>
            </Navbar>
            <VinylList />
            <Footer />
        </>
    )
}

interface LoginProps {
    login: () => void
}

const LoginContent: React.FC<LoginProps> = ({ login }) => {
    return (
        <Container style={{ paddingTop: "16px" }}>
            <WideButton
                title="Login with Google"
                onClick={login} />
        </Container>
    )
}

export default function Library() {
    const [logged, setLogged] = useState(false);
    const login = useGoogleLogin({
        onSuccess: (response) => {
            setLogged(true)
            debugger;
            // axios.defaults.headers.common['Authorization'] = `${response.token_type} ${response.access_token}`;
        },
        onError: () => {
            axios.defaults.headers.common['Authorization'] = "";
            setLogged(false)
        }, 
        flow: 'auth-code'
    });

    return (
        <>
            <Header />
            {logged && <LibraryContent />}
            {!logged && <LoginContent login={login} />}
        </>
    )
}