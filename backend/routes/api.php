<?php

use App\Http\Controllers\FileController;
use App\Http\Controllers\OAuthGoogleController;
use App\Http\Controllers\VinylController;
use App\Http\Middleware\Authenticate;
use Illuminate\Support\Facades\Route;

Route::middleware(['auth'])->group(function () {
    Route::controller(VinylController::class)->group(function () {
        Route::get('/vinyls', 'viewAll');
        Route::post('/vinyls', 'create');
        Route::get('/vinyls/{vinyl}', 'findOne');
        Route::put('/vinyls/{vinyl}', 'updateOne');
    });
});

Route::middleware(['auth'])->group(function () {
    Route::controller(FileController::class)->group(function () {
        Route::post('/files', 'create');
        Route::get('/files/{fileType}/{filePath}', 'findOne');
    });
});

Route::controller(OAuthGoogleController::class)->group(function () {
    Route::post('/oauth/google', 'exchange');
    Route::middleware(['auth'])->group(function () {
        Route::get('/oauth/google/refresh', 'refresh');
    });
});