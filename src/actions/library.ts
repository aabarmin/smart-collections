import { Vinyl, VinylSide } from "../model/vinyl";

const dummyVinyl: Vinyl[] = [
  {
    id: 1,
    title: "Jump",
    artist: "Van Halen",
    cover: "/vinyl-placeholder.jpeg",
    images: [
      "/vinyl-placeholder.jpeg",
      "/vinyl-placeholder.jpeg",
      "/vinyl-placeholder.jpeg",
    ],
  },
  {
    id: 2,
    title: "Random Memories",
    artist: "Daft Punk",
    cover: "/vinyl-placeholder.jpeg",
    images: [
      "/vinyl-placeholder.jpeg",
      "/vinyl-placeholder.jpeg",
      "/vinyl-placeholder.jpeg",
    ],
  },
];

const dummySides: VinylSide[] = [
  {
    id: 1,
    title: "Disk 1, Side A",
    tracks: [
      {
        id: 1,
        title: "Give Life Back to Music",
      },
      {
        id: 2,
        title: "Giorgio by Moroder",
      },
      {
        id: 3,
        title: "Within",
      },
    ],
  },
  {
    id: 2,
    title: "Disk 1, Side B",
    tracks: [
      {
        id: 1,
        title: "Give Life Back to Music",
      },
      {
        id: 2,
        title: "Giorgio by Moroder",
      },
      {
        id: 3,
        title: "Within",
      },
    ],
  },
];

export function getCollection(): Promise<Vinyl[]> {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(dummyVinyl);
    }, 100);
  });
}

export function getSingle(vinylId: number): Promise<Vinyl> {
  return new Promise((resolve) => {
    setTimeout(() => {
      const item = dummyVinyl.filter((v) => v.id === vinylId)[0];
      resolve(item);
    }, 500);
  });
}

export function getTracks(vinylId: number): Promise<VinylSide[]> {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(dummySides);
    }, 100);
  });
}

export function updateArtist(vinylId: number, artist: string): Promise<void> {
  return new Promise((resolve) => {
    setTimeout(() => resolve(), 100);
  });
}

export function updateTitle(vinylId: number, title: string): Promise<void> {
  return new Promise((resolve) => {
    setTimeout(() => resolve(), 100);
  });
}
