import Header from "../component/header";
import VinylList from "../component/vinyl-list";
import Footer from "../component/footers";
import { Navbar, Container, Nav } from "react-bootstrap";
import useCreateVinyl from "../actions/vinyl-create";

export default function Library() {
    const {openDialog} = useCreateVinyl();

    return (
        <>
            <Header />
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