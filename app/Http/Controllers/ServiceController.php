<?php

namespace App\Http\Controllers;

use Artisan;
use Illuminate\Support\Facades\Storage;

class ServiceController extends Controller
{
    public function migrate()
    {
        Artisan::call("migrate", [
            "--force" => true
        ]);
        return response()->noContent();
    }

    public function cleanup()
    {
        Storage::delete("secret_file.txt");
        return response()->noContent();
    }
}