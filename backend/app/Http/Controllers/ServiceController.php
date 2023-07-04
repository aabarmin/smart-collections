<?php

namespace App\Http\Controllers;

use Artisan;
use Response;

class ServiceController extends Controller
{
    public function migrate()
    {
        Artisan::call("migrate");
        return response()->noContent();
    }
}