export type VinylListItem = {
  vinylId: number;
  title: string;
  artist: string;
  cover: string;
};

type HasSides = {
  sides: VinylSide[];
};

type HasImages = {
  images: string[];
};

export type Vinyl = VinylListItem & HasSides & HasImages;

export type VinylSide = {
  sideId: number;
  title: string;
  tracks: VinylTrack[];
};

export type VinylTrack = {
  trackId: number;
  title: string;
};
