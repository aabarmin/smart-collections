import Header from "../component/header";
import VinylList from "../component/vinyl-list";
import Footer from "../component/footers";
import { Navbar, Container, Nav } from "react-bootstrap";
import useCreateVinyl from "../actions/vinyl-create";
import { useGoogleLogin } from "@react-oauth/google";
import React, { useEffect, useState } from "react";
import WideButton from "../component/button-wide";
import axios from "axios";
import { CodeExchangeResult, GoogleCodeResponse, exchangeCode } from "../actions/security";

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

const isValidToken = (tokenString: string | null): boolean => {
    if (tokenString === null) {
        return false; 
    }
    const parsedJson = JSON.parse(tokenString) as CodeExchangeResult;
    const currentTime = Date.now() / 1000; // convert to seconds
    return parsedJson.expires_at > currentTime;
};

const getJwt = (tokenString: string | null): {token: string, token_type: string} => {
    if (tokenString === null) {
        throw new Error("Token string should not be empty");
    }
    const parsedJson = JSON.parse(tokenString) as CodeExchangeResult;
    return {
        token: parsedJson.token, 
        token_type: parsedJson.token_type
    };
}

export default function Library() {
    const [logged, setLogged] = useState(false);
    useEffect(() => {
        const tokenString = localStorage.getItem("id_token");
        if (isValidToken(tokenString)) {
            setLogged(true);
            const jwt = getJwt(tokenString);
            axios.defaults.headers.common['Authorization'] = `${jwt.token_type} ${jwt.token}`;
        } else {
            setLogged(false);
            localStorage.removeItem("id_token");
        }
    });


    const login = useGoogleLogin({
        onSuccess: (response) => {
            const codeResponse: GoogleCodeResponse = {
                code: response.code
            }
            exchangeCode(codeResponse).then(exchangeResult => {
                localStorage.setItem("id_token", JSON.stringify(exchangeResult));
                axios.defaults.headers.common['Authorization'] = `${exchangeResult.token_type} ${exchangeResult.token}`;
                setLogged(true);
            });
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