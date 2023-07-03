<?php

namespace App\Http\Controllers;

use App\Http\Resources\VinylCollectionItemResource;
use App\Http\Resources\VinylCollectionResource;
use App\Http\Resources\VinylResource;
use App\Models\Vinyl;
use App\Models\VinylSide;
use App\Models\VinylTrack;
use Illuminate\Http\Request;

class VinylController extends Controller
{
    private $validationRules = [
        'vinyl_artist' => ['required', 'max:1024'],
        'vinyl_title' => ['required', 'max:1024'],

        'vinyl_images' => ['array', 'present'],
        'vinyl_images.*.image_path' => ['required'],

        'vinyl_sides' => ['array', 'present'],
        'vinyl_sides.*.side_title' => ['required', 'max:1024'],

        'vinyl_sides.*.side_tracks' => ['array', 'present'],
        'vinyl_sides.*.side_tracks.*.track_title' => ['required', 'max:1024']
    ];

    /**
     * View all the vinyls that belongs to the current user. 
     * @todo current user is not defined at the moment, so, just all the vinyls
     */
    public function viewAll(Request $request)
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
        $validated = $request->validate($this->validationRules);

        /**
         * Add images if present. 
         * 
         * @var Vinyl $vinyl
         */
        $vinyl = Vinyl::create($validated);
        if (count($validated['vinyl_images']) > 0) {
            $vinyl->images()->createMany($validated['vinyl_images']);
        }

        foreach ($validated['vinyl_sides'] as $side) {
            $vinylSide = VinylSide::create([
                'vinyl_id' => $vinyl->vinyl_id,
                'side_title' => $side['side_title']
            ]);
            foreach ($side['side_tracks'] as $track) {
                $vinylTrack = VinylTrack::create([
                    'side_id' => $vinylSide->side_id,
                    'track_title' => $track['track_title']
                ]);
            }
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
        $validated = $request->validate($this->validationRules);

        $vinyl->update([
            'vinyl_artist' => $validated['vinyl_artist'],
            'vinyl_title' => $validated['vinyl_title']
        ]);

        $vinyl->images()->delete();
        $vinyl->images()->createMany($validated['vinyl_images']);

        $vinyl->sides()->delete();

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