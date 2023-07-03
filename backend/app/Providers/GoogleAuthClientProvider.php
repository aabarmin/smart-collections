<?php

namespace App\Providers;

use App\Services\GoogleAuthClient;
use Illuminate\Foundation\Application;
use Illuminate\Support\ServiceProvider;

class GoogleAuthClientProvider extends ServiceProvider
{
    /**
     * Register services.
     */
    public function register(): void
    {
        $this->app->singleton(GoogleAuthClient::class, function (Application $app) {
            return new GoogleAuthClient(
                env("GOOGLE_CLIENT_ID"),
                env("GOOGLE_CLIENT_SECRET"),
                env("GOOGLE_REDIRECT_URI")
            );
        });
    }
}