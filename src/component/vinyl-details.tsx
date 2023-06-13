import { useEffect, useState } from "react";
import { Vinyl, VinylSide } from "../model/vinyl";
import { Container, Nav, Navbar } from "react-bootstrap";
import Covers from "./covers";
import { useParams } from "react-router-dom";
import { getSingle } from "../actions/library";
import Loader from "./loader";
import { getTracks } from "../actions/library";
import VinylSides from "./vinyl-sides";
import { NavLink } from "react-router-dom";

export default function VinylDetails() {
    const params = useParams();
    const id = params.id ? parseInt(params.id) : 0;

    const [vinyl, setItem] = useState(null as Vinyl | null);
    const [sides, setSides] = useState([] as VinylSide[]);

    useEffect(() => {
        getSingle(id)
            .then(vinyl => {
                setItem(vinyl);
            });

        getTracks(id)
            .then(sides => {
                setSides(sides);
            });
    }, [id]);

    if (vinyl == null) {
        return <Loader />
    }

    return (
        <>
            <Navbar bg="light">
                <Container>
                    <Nav>
                        <Nav.Item>
                            <NavLink 
                                to="/library" 
                                className="nav-link">
                                Back
                            </NavLink>
                        </Nav.Item>
                        <Nav.Item>
                            <NavLink 
                                to={`/library/${vinyl.id}/edit`} 
                                className="nav-link">
                                Edit
                            </NavLink>
                        </Nav.Item>
                    </Nav>
                    <Navbar.Brand>{vinyl.title}</Navbar.Brand>
                </Container>
            </Navbar>
            <Container>
                <Covers images={vinyl.images} editable={false} />
                <h2>{vinyl.title}</h2>
                <small>by {vinyl.artist}</small>
                <VinylSides sides={sides} />
            </Container>
        </>
    )
}
