export type VinylListItem = {
  vinyl_id: number;
  vinyl_title: string;
  vinyl_artist: string;
  cover?: string;
};

type HasSides = {
  vinyl_sides: VinylSide[];
};

type HasImages = {
  vinyl_images: string[];
};

export type Vinyl = VinylListItem & HasSides & HasImages;

export type VinylSide = {
  side_id: number;
  side_title: string;
  side_tracks: VinylTrack[];
};

export type VinylTrack = {
  track_id: number;
  track_title: string;
};
