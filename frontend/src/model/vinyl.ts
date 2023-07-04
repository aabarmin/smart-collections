export type VinylListItem = {
  vinyl_id: number;
  vinyl_title: string;
  vinyl_artist: string;
  vinyl_images: VinylImage[];
};

export type VinylImage = {
  image_id: number;
  image_path: string;
};

type HasSides = {
  vinyl_sides: VinylSide[];
};

export type Vinyl = VinylListItem & HasSides;

export type VinylSide = {
  side_id: number;
  side_title: string;
  side_tracks: VinylTrack[];
};

export type VinylTrack = {
  track_id: number;
  track_title: string;
};
