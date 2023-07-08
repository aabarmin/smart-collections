<?php

namespace App\Http\Controllers;

use App\Http\Resources\VinylCollectionResource;
use App\Http\Resources\VinylResource;
use App\Models\User;
use App\Models\Vinyl;
use App\Models\VinylImage;
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
        $records = Vinyl::where('user_id', $request->user()->user_id)
            ->orderByDesc('created_at')
            ->get();
        $data = [
            'records' => $records
        ];
        return view('library/index', $data);
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
            'vinyl_disks' => ['required']
        ]);

        /**
         * @var Vinyl $vinyl
         */
        $validated['user_id'] = $request->user()->user_id;
        $vinyl = Vinyl::create($validated);
        for ($disk = 0; $disk < $validated['vinyl_disks']; $disk++) {
            for ($side = 0; $side < 2; $side++) {
                $disk_number = $disk + 1;
                $side_letter = $side % 2 == 0 ? 'A' : 'B';
                $side_title = "Disk {$disk_number}, Side {$side_letter}";
                VinylSide::create([
                    'vinyl_id' => $vinyl->vinyl_id,
                    'side_title' => $side_title
                ]);
            }
        }

        return redirect('/library');
    }

    /**
     * Display a single vinyl.
     */
    public function findOne(Vinyl $vinyl)
    {
        return view('library/single', [
            'vinyl' => $vinyl
        ]);
    }

    /**
     * Display an edit page.
     */
    public function editOne(Vinyl $vinyl)
    {
        return view('library/edit', [
            'vinyl' => $vinyl
        ]);
    }

    public function updateOne(Request $request, Vinyl $vinyl)
    {
        $validated = $request->validate([
            'vinyl_artist' => ['required', 'max:1024'],
            'vinyl_title' => ['required', 'max:1024'],

            'vinyl_images' => ['array'],
            'vinyl_images.*.image_path' => ['required'],

            'vinyl_sides' => ['array'],
            'vinyl_sides.*.side_title' => ['required', 'max:1024'],

            'vinyl_sides.*.side_tracks' => ['array'],
            'vinyl_sides.*.side_tracks.*.track_title' => ['required', 'max:1024']
        ]);

        $vinyl->sides()->delete();
        $vinyl->images()->delete();

        if (array_key_exists('vinyl_images', $validated)) {
            foreach ($validated['vinyl_images'] as $image) {
                VinylImage::create([
                    'vinyl_id' => $vinyl->vinyl_id,
                    'image_path' => $image['image_path']
                ]);
            }
        }

        foreach ($validated['vinyl_sides'] as $side) {
            $vinyl_side = VinylSide::create([
                'vinyl_id' => $vinyl->vinyl_id,
                'side_title' => $side['side_title']
            ]);

            if (!array_key_exists("side_tracks", $side)) {
                continue;
            }
            foreach ($side['side_tracks'] as $track) {
                VinylTrack::create([
                    'side_id' => $vinyl_side->side_id,
                    'track_title' => $track['track_title']
                ]);
            }
        }

        $vinyl->vinyl_title = $validated['vinyl_title'];
        $vinyl->vinyl_artist = $validated['vinyl_artist'];
        $vinyl->save();

        return view('library/single', [
            'vinyl' => $vinyl
        ]);
    }
}
