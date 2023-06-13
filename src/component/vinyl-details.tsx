import { useEffect, useState } from "react";
import { Vinyl, VinylSide } from "../model/vinyl";
import { Container, Nav, Navbar } from "react-bootstrap";
import Covers from "./covers";
import { useParams } from "react-router-dom";
import { getSingle } from "../actions/library";
import Loader from "./loader";
import { NavLink } from "react-router-dom";

interface NavigationProps {
    vinyl: Vinyl
}

interface SideProps {
    side: VinylSide
}

const DetailsNavigation: React.FC<NavigationProps> = ({vinyl}) => {
    return (
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
                            to={`/library/${vinyl.vinylId}/edit`} 
                            className="nav-link">
                            Edit
                        </NavLink>
                    </Nav.Item>
                </Nav>
                <Navbar.Brand>{vinyl.title}</Navbar.Brand>
            </Container>
        </Navbar>
    )
}

const VinylSideItem: React.FC<SideProps> = ({side}) => {
    return (
        <section>
            <h2>{side.title}</h2>
            <ul>
                {side.tracks.map(track => (
                    <li key={track.id}>
                        {track.title}
                    </li>
                ))}
            </ul>
        </section>
    )
}

export default function VinylDetails() {
    const params = useParams();
    const id = params.id ? parseInt(params.id) : 0;

    const [vinyl, setItem] = useState(null as Vinyl | null);

    useEffect(() => {
        getSingle(id)
            .then(vinyl => {
                setItem(vinyl);
            });
    }, [id]);

    if (vinyl == null) {
        return <Loader />
    }

    return (
        <>
            <DetailsNavigation vinyl={vinyl} />
            <Container>
                <Covers images={vinyl.images} editable={false} />
                <h2>{vinyl.title}</h2>
                <small>by {vinyl.artist}</small>
                {vinyl.sides.map(side => (
                    <VinylSideItem 
                        key={side.id}
                        side={side} />
                ))}
            </Container>
        </>
    )
}
