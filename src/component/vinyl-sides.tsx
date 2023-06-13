import { VinylSide } from "../model/vinyl";

interface Props {
    sides: VinylSide[]
}

export default function VinylSides({
    sides
} : Props) {
    return (
        <>
            {sides.map(side => (
                <section key={side.id}>
                    <h2>{side.title}</h2>
                    <ul>
                        {side.tracks.map(track => (
                            <li key={track.id}>
                                {track.title}
                            </li>
                        ))}
                    </ul>
                </section>
            ))}
        </>
    )
}