<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;

class FileController extends Controller
{
    public function create(Request $request)
    {
        $validated = $request->validate([
            'file' => ['required']
        ]);
        $path = $request->file('file')->store('covers');

        return response()
            ->noContent(201)
            ->header("Location", "/api/files/$path");
    }

    public function findOne(Request $request, string $fileType, string $filePath)
    {
        return Storage::download($fileType . '/' . $filePath);
    }
}