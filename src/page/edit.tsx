import Header from "../component/header";
import Footer from "../component/footers";
import { Navbar, Container, Nav, Button } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import FormText from "../component/form-text";
import { SubmitHandler, useFieldArray, useForm } from "react-hook-form";
import { useCallback, useEffect } from "react";
import { getSingle, getTracks, updateVinyl } from "../actions/library";
import Loader from "../component/loader";
import { createSide } from "../actions/library";
import { VinylSide, VinylTrack } from "../model/vinyl";
import { updateSide } from "../actions/library";
import { NavLink } from "react-router-dom";

type InputTrack = {
    trackId: number;
    title: string; 
};

type InputSide = {
    sideId: number; 
    title: string; 
    tracks: InputTrack[]
};

type Inputs = {
    title: string; 
    artist: string; 
    sides: InputSide[];
}

interface ButtonProps {
    onClick: () => void, 
    title: string, 
    variant?: string
}

interface SubmitProps {
    title: string, 
    variant?: string
}

const WideButton: React.FC<ButtonProps> = ({
    onClick, 
    title, 
    variant = "outline-primary"
}) => {
    return (
        <div className="d-grid gap-2">
            <Button 
                onClick={onClick}
                variant={variant} 
                size="lg">
                {title}
            </Button>
        </div>
    )
}

const WideSubmit: React.FC<SubmitProps> = ({
    title, 
    variant = "primary"
}) => {
    return (
        <div className="d-grid gap-2">
            <Button 
                variant={variant} 
                type="submit"
                size="lg">
                {title}
            </Button>
        </div>
    )
};

export default function PageEdit() {
    const params = useParams();
    const id = params.id ? parseInt(params.id) : 0;
    const navigate = useNavigate();
    const {register, setValue, control, getValues, handleSubmit} = useForm<Inputs>({
        defaultValues: {
            sides: []
        }
    });
    const {fields, append} = useFieldArray({
        control, 
        name: "sides"
    });
    const onSideAdd = useCallback(() => {
        append({
            sideId: 0, 
            title: "New side", 
            tracks: []
        })
    }, [append]);
    const onTrackAdd = useCallback((sideIndex: number) => {
        const sides: InputSide[] = getValues("sides")
        sides[sideIndex].tracks.push({
            trackId: 0, 
            title: "New track"
        });
        setValue("sides", sides);
    }, [getValues, setValue]);
    const onFormSubmit: SubmitHandler<Inputs> = (data) => {
        const promises: Promise<any>[] = [];
        promises.push(updateVinyl(id, {
            id: id, 
            title: data.title, 
            artist: data.artist, 
            cover: "", 
            images: []
        }));
        const sidePromises = data.sides.map(side => {
            const tracks: VinylTrack[] = side.tracks.map(track => ({
                id: track.trackId, 
                title: track.title
            }));

            const vinylSide: VinylSide = {
                id: side.sideId, 
                title: side.title, 
                tracks: tracks
            }

            if (side.sideId === 0) {
                return createSide(id, vinylSide);
            }
            return updateSide(id, side.sideId, vinylSide);
        })
        sidePromises.forEach(p => promises.push(p));

        Promise
            .all(promises)
            .then(() => {
                navigate(`/library/${id}`)
            });
    }

    useEffect(() => {
        getSingle(id)
            .then(vinyl => {
                setValue("title", vinyl.title);
                setValue("artist", vinyl.artist);
            });

        getTracks(id)
            .then(sides => {
                const forForm: InputSide[] = sides.map(side => ({
                    sideId: side.id, 
                    title: side.title, 
                    tracks: side.tracks.map(track => ({
                        trackId: track.id, 
                        title: track.title
                    }))
                }))
                setValue("sides", forForm)
            })
    }, [id, setValue]);

    if (id === 0) {
        return <Loader />
    }

    return (
        <>
            <Header />
            <Navbar bg="light">
                <Container>
                    <Nav>
                        <Nav.Item>
                            <NavLink to={`/library/${id}`} className="nav-link">
                                Back
                            </NavLink>
                        </Nav.Item>
                    </Nav>
                </Container>
            </Navbar>
            <Container>
                <form onSubmit={handleSubmit(onFormSubmit)}>
                    <h2>Vinyl Details</h2>

                    <FormText 
                        id="title"
                        label="Title"
                        register={register}  />
                    <FormText 
                        id="artist"
                        label="Artist" 
                        register={register} />

                    <h2>Vinyl Tracks</h2>

                    <WideButton 
                        onClick={onSideAdd} 
                        title="Add side" 
                    />

                    <hr />

                    {fields.map((side, sideIndex) => (
                        <div key={`sides.${side.id}`}>
                            <FormText 
                                key={`sides.${side.id}`}
                                id={`sides.${sideIndex}.title` as const}
                                label="Side Name"
                                register={register}
                            />

                            {side.tracks.map((track, trackIndex) => (
                                <FormText
                                    key={`sides.${side.id}.tracks.${track.trackId}`}
                                    id={`sides.${sideIndex}.tracks.${trackIndex}.title`}
                                    label="Track Name"
                                    register={register}
                                />
                            ))}

                            <WideButton 
                                title="Add track"
                                onClick={() => onTrackAdd(sideIndex)}
                            />

                            <hr />
                        </div>
                    ))}

                    <WideSubmit 
                        title="Save"
                    />

                    <hr />

                </form>
            </Container>
            <Footer />
        </>
    )
}

