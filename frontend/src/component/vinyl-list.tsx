import React, { useEffect, useState } from "react";
import { getCollection } from "../actions/library";
import Loader from "./loader";
import VinylItem from "./vinyl-item";
import { VinylListItem } from "../model/vinyl";
import { useNavigate } from "react-router";
import { Container } from "react-bootstrap";

export default function VinylList() {
    const router = useNavigate();
    const [loading, setLoading] = useState(true);
    const [records, setRecords] = useState([] as VinylListItem[]);

    useEffect(() => {
        setLoading(true);
        getCollection()
            .then(items => {
                setRecords(items)
            })
            .catch(() => {
                console.log("Something went wrong");
            })
            .finally(() => {
                setLoading(false);
            });
    }, []);

    if (loading) {
        return (<Loader />);
    }

    const navigate = (id: number) => {
        router("/library/" + id);
    };

    return (
        <Container>
            {records.map(item => {
                return <VinylItem key={item.vinylId} vinyl={item} onClick={navigate} />
            })}
        </Container>
    );
}