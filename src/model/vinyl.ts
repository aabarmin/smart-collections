export type Vinyl = {
  id: number;
  title: string;
  artist: string;
  cover: string;
  images: string[];
};

export type VinylSide = {
  id: number;
  title: string;
  tracks: VinylTrack[];
};

export type VinylTrack = {
  id: number;
  title: string;
};
