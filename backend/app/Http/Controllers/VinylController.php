<?php

namespace App\Http\Controllers;

use App\Http\Resources\VinylCollectionItemResource;
use App\Http\Resources\VinylCollectionResource;
use App\Http\Resources\VinylResource;
use App\Models\Vinyl;
use App\Models\VinylImage;
use App\Models\VinylSide;
use App\Models\VinylTrack;
use Illuminate\Http\Request;

class VinylController extends Controller
{
    /**
     * View all the vinyls that belongs to the current user. 
     * @todo current user is not defined at the moment, so, just all the vinyls
     */
    public function viewAll()
    {
        return response()
            ->json(
                new VinylCollectionResource(
                    VinylCollectionItemResource::collection(Vinyl::all())
                )
            );
    }

    /**
     * Create a new record. 
     * @todo should automatically belong to the current user.
     */
    public function create(Request $request)
    {
        $validated = $request->validate([
            'vinyl_artist' => ['required', 'max:1024'],
            'vinyl_title' => ['required', 'max:1024'],
            'vinyl_images' => ['array']
        ]);

        $vinyl = Vinyl::create($validated);
        foreach ($validated['vinyl_images'] as $image) {
            $vinyl->images()->create([
                'image_path' => $image
            ]);
        }

        return response()
            ->noContent(201)
            ->header('Location', "/api/vinyls/$vinyl->vinyl_id");
    }

    /**
     * Display the specified resource.
     */
    public function findOne(Vinyl $vinyl)
    {
        return response()
            ->json(VinylResource::make($vinyl));
    }

    /**
     * Update the specified resource in storage.
     */
    public function updateOne(Request $request, Vinyl $vinyl)
    {
        $validated = $request->validate([
            'vinyl_artist' => ['required', 'max:1024'],
            'vinyl_title' => ['required', 'max:1024'],

            'vinyl_images' => ['array'],
            'vinyl_images.*.image_path' => ['required'],

            'vinyl_sides' => ['array', 'required'],
            'vinyl_sides.*.side_title' => ['required', 'max:1024'],

            'vinyl_sides.*.side_tracks' => ['array', 'required'],
            'vinyl_sides.*.side_tracks.*.track_title' => ['required', 'max:1024']
        ]);

        $vinyl->update([
            'vinyl_artist' => $validated['vinyl_artist'],
            'vinyl_title' => $validated['vinyl_title']
        ]);

        $vinyl->images()->delete();
        $vinyl->images()->createMany($validated['vinyl_images']);

        /**
         * @var VinylSide $vinylSide
         */
        foreach ($vinyl->sides()->lazy() as $vinylSide) {
            $vinylSide->tracks()->delete();
            $vinylSide->delete();
        }

        foreach ($validated['vinyl_sides'] as $side) {
            $vinyl_side = VinylSide::create([
                'vinyl_id' => $vinyl->vinyl_id,
                'side_title' => $side['side_title']
            ]);

            foreach ($side['side_tracks'] as $track) {
                VinylTrack::create([
                    'side_id' => $vinyl_side->side_id,
                    'track_title' => $track['track_title']
                ]);
            }
        }

        $vinyl->save();

        return response()
            ->noContent(204);
    }
}