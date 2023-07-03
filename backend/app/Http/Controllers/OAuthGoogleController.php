<?php

namespace App\Http\Controllers;

use App\Http\Resources\TokenExchangeResource;
use App\Models\Auth\CodeExchangeRequest;
use App\Services\GoogleAuthService;
use Illuminate\Http\Request;

class OAuthGoogleController extends Controller
{
    public function __construct(
        private GoogleAuthService $auth_service
    ) {

    }

    public function exchange(Request $request)
    {
        $validated = $request->validate([
            'code' => ['required', 'max:512']
        ]);

        $exchange_request = CodeExchangeRequest::create($validated);
        $exchange_response = $this->auth_service->exchange($exchange_request);

        return TokenExchangeResource::make($exchange_response);
    }

    public function refresh(Request $request)
    {
        $user = $request->user();
        $refresh_response = $this->auth_service->refresh($user->email);

        return TokenExchangeResource::make($refresh_response);
    }
}