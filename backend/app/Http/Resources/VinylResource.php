<?php

namespace App\Http\Resources;

use App\Models\VinylImage;
use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class VinylResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @return array<string, mixed>
     */
    public function toArray(Request $request): array
    {
        return [
            'vinyl_id' => $this->vinyl_id,
            'vinyl_title' => $this->vinyl_title,
            'vinyl_artist' => $this->vinyl_artist,
            'vinyl_images' => VinylImageResource::collection($this->images),
            'vinyl_sides' => VinylSideResource::collection($this->sides)
        ];
    }
}