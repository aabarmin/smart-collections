import { Vinyl, VinylListItem, VinylSide } from "../model/vinyl";

const dummySides: VinylSide[] = [
  {
    sideId: 1,
    title: "Disk 1, Side A",
    tracks: [
      {
        trackId: 1,
        title: "Give Life Back to Music",
      },
      {
        trackId: 2,
        title: "Giorgio by Moroder",
      },
      {
        trackId: 3,
        title: "Within",
      },
    ],
  },
  {
    sideId: 2,
    title: "Disk 1, Side B",
    tracks: [
      {
        trackId: 1,
        title: "Give Life Back to Music",
      },
      {
        trackId: 2,
        title: "Giorgio by Moroder",
      },
      {
        trackId: 3,
        title: "Within",
      },
    ],
  },
];

const dummyVinyl: Vinyl[] = [
  {
    vinylId: 1,
    title: "Jump",
    artist: "Van Halen",
    cover: "/vinyl-placeholder.jpeg",
    images: [
      "/vinyl-placeholder.jpeg",
      "/vinyl-placeholder.jpeg",
      "/vinyl-placeholder.jpeg",
    ],
    sides: dummySides,
  },
  {
    vinylId: 2,
    title: "Random Memories",
    artist: "Daft Punk",
    cover: "/vinyl-placeholder.jpeg",
    images: [
      "/vinyl-placeholder.jpeg",
      "/vinyl-placeholder.jpeg",
      "/vinyl-placeholder.jpeg",
    ],
    sides: dummySides,
  },
];

export function getCollection(): Promise<VinylListItem[]> {
  // return axios.get("/vinyls")
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(dummyVinyl);
    }, 100);
  });
}

export function getSingle(vinylId: number): Promise<Vinyl> {
  return new Promise((resolve) => {
    setTimeout(() => {
      const item = dummyVinyl.filter((v) => v.vinylId === vinylId)[0];
      resolve(item);
    }, 500);
  });
}

export function updateVinyl(vinylId: number, vinyl: Vinyl): Promise<Vinyl> {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(vinyl);
    }, 100);
  });
}

export function createVinyl(
  title: string,
  artist: string,
  sideNumber: number
): Promise<Vinyl> {
  return new Promise((resolve) => {
    const vinyl: Vinyl = {
      vinylId: dummyVinyl.length + 1,
      title: title,
      artist: artist,
      cover: "/vinyl-placeholder.jpeg",
      images: ["/vinyl-placeholder.jpeg"],
      sides: [],
    };
    dummyVinyl.push(vinyl);
    console.log(dummyVinyl);
    resolve(vinyl);
  });
}

export function uploadFile(file: File): Promise<string> {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve("/vinyl-placeholder.jpeg");
    }, 100);
  });
}
