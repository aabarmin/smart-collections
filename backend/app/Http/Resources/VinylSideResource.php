<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class VinylSideResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @return array<string, mixed>
     */
    public function toArray(Request $request): array
    {
        return [
            'side_id' => $this->side_id,
            'side_title' => $this->side_title,
            'side_tracks' => VinylTrackResource::collection($this->tracks)
        ];
    }
}