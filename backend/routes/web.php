<?php

use App\Http\Controllers\Auth\LoginController;
use App\Http\Controllers\HomeController;
use App\Http\Controllers\VinylController;
use Illuminate\Support\Facades\Route;

Route::controller(HomeController::class)->group(function () {
    Route::get('/', 'home');
});

Route::controller(VinylController::class)->group(function () {
    Route::middleware(['auth'])->group(function () {
        Route::get('/library', 'viewAll');
        Route::get('/library/{vinyl}', 'findOne');
        Route::get('/library/{vinyl}/edit', 'editOne');
        Route::post('/library/{vinyl}', 'updateOne');
        Route::post('/library', 'create');
    });
});

Route::controller(LoginController::class)->group(function () {
    Route::get('/login', 'login')->name('login');
    Route::get('/login/google', 'loginWithGoogle');
    Route::get('/login/google/callback', 'callback');
});
