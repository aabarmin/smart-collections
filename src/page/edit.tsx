import Header from "../component/header";
import Footer from "../component/footers";
import { Navbar, Container, Nav, Button, Image, Row, Col } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import FormText from "../component/form-text";
import { SubmitHandler, useFieldArray, useForm } from "react-hook-form";
import { ChangeEvent, useCallback, useEffect, useRef, useState } from "react";
import { getSingle, updateVinyl, uploadFile } from "../actions/library";
import Loader from "../component/loader";
import { Vinyl } from "../model/vinyl";
import { NavLink } from "react-router-dom";
import _ from "lodash";
import FormTextWithButton from "../component/form-text-with-button";
import WideButton from "../component/button-wide";

type InputTrack = {
    trackId: number;
    title: string;
};

type InputSide = {
    sideId: number;
    title: string;
    tracks: InputTrack[]
};

type InputImage = {
    path: string;
};

type Inputs = {
    title: string;
    artist: string;
    images: InputImage[];
    sides: InputSide[];
}

interface SubmitProps {
    title: string,
    variant?: string
}

interface HeaderProps {
    vinylId: number;
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

const PageHeader: React.FC<HeaderProps> = ({ vinylId }) => {
    return (
        <Navbar bg="light">
            <Container>
                <Nav>
                    <Nav.Item>
                        <NavLink to={`/library/${vinylId}`} className="nav-link">
                            Back
                        </NavLink>
                    </Nav.Item>
                </Nav>
            </Container>
        </Navbar>
    )
}

export default function PageEdit() {
    const params = useParams();
    const id = params.id ? parseInt(params.id) : 0;
    const [loaded, setLoaded] = useState(false);
    const navigate = useNavigate();
    const uploadInputRef = useRef<HTMLInputElement | null>(null);
    const { register, setValue, control, getValues, handleSubmit } = useForm<Inputs>({
        defaultValues: {
            sides: [],
            images: []
        }
    });
    const {
        fields: sideFields,
        append: sideAppend
    } = useFieldArray({
        control,
        name: "sides"
    });
    const {
        fields: imageFields,
        append: imageAppend
    } = useFieldArray({
        control,
        name: "images"
    })
    const onSideAdd = useCallback(() => {
        sideAppend({
            sideId: 0,
            title: "New side",
            tracks: []
        })
    }, [sideAppend]);
    const onAddImageButtonClick = useCallback(() => {
        uploadInputRef.current?.click();
    }, []);
    const onAddImage = useCallback((e: ChangeEvent<HTMLInputElement>) => {
        if (!e.target.files) {
            return;
        }

        uploadFile(e.target.files[0]).then(path => {
            imageAppend({
                path
            })
        });
    }, [imageAppend]);
    const onTrackAdd = useCallback((sideIndex: number) => {
        const sides: InputSide[] = getValues("sides")
        sides[sideIndex].tracks.push({
            trackId: 0,
            title: "New track"
        });
        setValue("sides", sides);
    }, [getValues, setValue]);
    const onTrackRemove = useCallback((trackId: number) => {
        const sides: InputSide[] = getValues("sides");
        sides.forEach(side => {
            _.remove(side.tracks, (track) => {
                return track.trackId === trackId
            })
        })
        setValue("sides", sides);
    }, [getValues, setValue]);
    const onFormSubmit: SubmitHandler<Inputs> = (data) => {
        const vinyl: Vinyl = {
            vinylId: id, 
            title: data.title, 
            artist: data.artist, 
            images: data.images.map(image => image.path),
            cover: "", // fixit
            sides: data.sides.map(side => ({
                id: side.sideId, 
                title: side.title, 
                tracks: side.tracks.map(track => ({
                    id: track.trackId, 
                    title: track.title
                }))
            }))
        }
        updateVinyl(id, vinyl).then(() => {
            navigate(`/library/${id}`)
        });
    }

    useEffect(() => {
        getSingle(id)
            .then(vinyl => {
                setValue("title", vinyl.title);
                setValue("artist", vinyl.artist);
                setValue("images", vinyl.images.map(img => ({
                    path: img
                })));
                setValue("sides", vinyl.sides.map(side => ({
                    sideId: side.id, 
                    title: side.title, 
                    tracks: side.tracks.map(track => ({
                        trackId: track.id, 
                        title: track.title
                    }))
                })))
            })
            .finally(() => {
                setLoaded(true);
            });
    }, [id, setValue, setLoaded]);

    if (!loaded) {
        return (
            <>
                <Header />
                <Loader />
            </>
        )
    }

    return (
        <>
            <Header />
            <PageHeader vinylId={id} />
            <Container>
                <form onSubmit={handleSubmit(onFormSubmit)}>
                    <h2>Vinyl Details</h2>

                    <FormText
                        id="title"
                        label="Title"
                        register={register} />
                    <FormText
                        id="artist"
                        label="Artist"
                        register={register} />

                    <h2>Vinyl Images</h2>

                    {imageFields.length > 0 && (
                        <Container style={{ paddingBottom: "16px" }}>
                            {_.chunk(imageFields, 3).map((row, rowIndex) => (
                                <Row key={rowIndex}>
                                    {row.map((img, imgIndex) => (
                                        <Col key={img.id} xs={4}>
                                            <Image
                                                src={img.path}
                                                thumbnail
                                            />
                                        </Col>
                                    ))}
                                </Row>
                            ))}
                        </Container>
                    )}

                    <input
                        style={{ display: "none" }}
                        type="file"
                        onChange={onAddImage}
                        ref={uploadInputRef} />

                    <WideButton
                        onClick={onAddImageButtonClick}
                        title="Add image"
                    />

                    <hr />

                    <h2>Vinyl Tracks</h2>

                    <WideButton
                        onClick={onSideAdd}
                        title="Add side"
                    />

                    <hr />

                    {sideFields.map((side, sideIndex) => (
                        <div key={`sides.${side.id}`}>
                            <FormText
                                key={`sides.${side.id}`}
                                id={`sides.${sideIndex}.title` as const}
                                label="Side Name"
                                register={register}
                            />

                            {side.tracks.map((track, trackIndex) => (
                                <FormTextWithButton
                                    key={`sides.${side.id}.tracks.${track.trackId}`}
                                    id={`sides.${sideIndex}.tracks.${trackIndex}.title`}
                                    label="Track Name"
                                    buttonLabel="X"
                                    onClick={() => onTrackRemove(track.trackId)}
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

