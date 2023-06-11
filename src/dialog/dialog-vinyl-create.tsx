import React from "react";
import { Button, FloatingLabel, Form, Modal } from "react-bootstrap";
import useCreateVinyl from "../actions/vinyl-create";
import { SubmitHandler, useForm } from "react-hook-form";
import { createVinyl } from "../actions/library";

type Inputs = {
    title: string; 
    artist: string; 
    disks: number; 
}

export default function DialogVinylCreate() {
    const {opened, closeDialog} = useCreateVinyl(); 
    const {register, handleSubmit} = useForm<Inputs>({
        defaultValues: {
            disks: 1
        }
    });
    const onSubmit: SubmitHandler<Inputs> = values => {
        createVinyl(values.title, values.artist, values.disks).then(() => {
            closeDialog();
        });
    };

    return (
        <Modal show={opened} onHide={closeDialog}>
            <form onSubmit={handleSubmit(onSubmit)}>
                <Modal.Dialog>
                    <Modal.Header closeButton>
                        <Modal.Title>Add a vinyl</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form.Group>
                            <FloatingLabel 
                                label="Album Title" 
                                controlId="albumTitle">
                                <Form.Control 
                                    type="text" 
                                    {...register("title", {required: true})}
                                    placeholder="" />
                            </FloatingLabel>
                        </Form.Group>

                        <FloatingLabel 
                            label="Artist" 
                            controlId="albumArtist">
                            <Form.Control 
                                type="text" 
                                {...register("artist", {required: true})}
                                placeholder="" />
                        </FloatingLabel>

                        <FloatingLabel 
                            label="Number of disks" 
                            controlId="disks">
                            <Form.Select
                                defaultValue={1}
                                {...register("disks")}>
                                <option value={1}>1</option>
                                <option value={2}>2</option>
                                <option value={3}>3</option>
                            </Form.Select>
                        </FloatingLabel>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="primary" type="submit">
                            Create
                        </Button>
                    </Modal.Footer>
                </Modal.Dialog>
            </form>
        </Modal>
    );
}