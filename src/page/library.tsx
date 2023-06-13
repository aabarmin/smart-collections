import Header from "../component/header";
import VinylList from "../component/vinyl-list";
import Footer from "../component/footers";
import { Navbar, Container, Nav } from "react-bootstrap";
import useCreateVinyl from "../actions/vinyl-create";
import { TokenResponse, useGoogleLogin } from "@react-oauth/google";
import React, { useState } from "react";
import WideButton from "../component/button-wide";
import LoginContext from "../context/login-context";
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
    const [profile, setProfile] = useState<TokenResponse | null>(null);
    const login = useGoogleLogin({
        onSuccess: (response) => {
            setLogged(true)
            setProfile(response)
            axios.defaults.headers.common['Authorization'] = `${response.token_type} ${response.access_token}`;
        },
        onError: () => {
            axios.defaults.headers.common['Authorization'] = "";
            setLogged(false)
        }
    });

    return (
        <>
            <Header />
            {logged && (
                <LoginContext.Provider value={profile}>
                    <LibraryContent />
                </LoginContext.Provider>
            )}
            {!logged && <LoginContent login={login} />}
        </>
    )
}