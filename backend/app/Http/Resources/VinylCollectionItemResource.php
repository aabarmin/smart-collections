<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class VinylCollectionItemResource extends JsonResource
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
            'vinyl_artist' => $this->vinyl_artist
        ];
    }
}