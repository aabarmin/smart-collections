import axios from "axios";
import { Vinyl, VinylImage, VinylListItem, VinylSide } from "../model/vinyl";
import { BackendResponseCollection, BackendResponseSingle } from "./response";

const baseUrl = process.env.REACT_APP_BACKED_URL;

export function getCollection(): Promise<VinylListItem[]> {
  return new Promise((resolve) => {
    const url = `${baseUrl}/vinyls`;
    axios
      .get<BackendResponseCollection<VinylListItem>>(url)
      .then((response) => {
        const collectionResponse = response.data;
        const items = collectionResponse.data;
        resolve(items);
      });
  });
}

export function getSingle(vinylId: number): Promise<Vinyl> {
  return new Promise((resolve) => {
    const url = `${baseUrl}/vinyls/${vinylId}`;
    axios.get<Vinyl>(url).then((response) => {
      resolve(response.data);
    });
  });
}

export function updateVinyl(vinylId: number, vinyl: Vinyl): Promise<Vinyl> {
  return new Promise((resolve) => {
    const url = `${baseUrl}/vinyls/${vinylId}`;
    axios.put<Vinyl>(url, vinyl).then((updateResponse) => {
      resolve(updateResponse.data);
    });
  });
}

export function createVinyl(
  title: string,
  artist: string,
  numberOfDisks: number
): Promise<Vinyl> {
  const url = `${baseUrl}/vinyls`;
  const sides: VinylSide[] = [];
  for (let disk = 0; disk < numberOfDisks; disk++) {
    for (let side = 0; side < 2; side++) {
      const vinylSide: VinylSide = {
        side_id: 0,
        side_title: `Disk ${disk + 1} Side ${String.fromCharCode(65 + side)}`,
        side_tracks: [],
      };
      sides.push(vinylSide);
    }
  }

  const body: Vinyl = {
    vinyl_id: 0,
    vinyl_sides: sides,
    vinyl_images: [],
    vinyl_title: title,
    vinyl_artist: artist,
  };

  return new Promise((resolve) => {
    axios.post(url, body).then((createResponse) => {
      const locationHeader = createResponse.headers.location;
      const vinyl_id = parseInt(
        locationHeader.substring(locationHeader.lastIndexOf("/") + 1)
      );
      getSingle(vinyl_id).then((vinyl) => {
        resolve(vinyl);
      });
    });
  });
}

export function uploadFile(file: File): Promise<string> {
  return new Promise((resolve) => {
    const url = `${baseUrl}/files`;
    const formData: FormData = new FormData();
    formData.append("file", file);

    axios
      .post(url, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        const locationHeader = response.headers.location;
        const imagePath = locationHeader.substring(11);
        resolve(imagePath);
      });
  });
}

export function getImagePath(image: VinylImage): string {
  return `${baseUrl}/files/${image.image_path}`;
}
