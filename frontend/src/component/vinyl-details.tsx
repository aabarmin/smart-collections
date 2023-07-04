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
                            to={`/library/${vinyl.vinyl_id}/edit`} 
                            className="nav-link">
                            Edit
                        </NavLink>
                    </Nav.Item>
                </Nav>
                <Navbar.Brand>{vinyl.vinyl_title}</Navbar.Brand>
            </Container>
        </Navbar>
    )
}

const VinylSideItem: React.FC<SideProps> = ({side}) => {
    return (
        <section>
            <h2>{side.side_title}</h2>
            <ul>
                {side.side_tracks.map(track => (
                    <li key={track.track_id}>
                        {track.track_title}
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
                <Covers images={vinyl.vinyl_images} editable={false} />
                <h2>{vinyl.vinyl_title}</h2>
                <small>by {vinyl.vinyl_artist}</small>
                {vinyl.vinyl_sides.map(side => (
                    <VinylSideItem 
                        key={side.side_id}
                        side={side} />
                ))}
            </Container>
        </>
    )
}
