<?php

namespace App\Services;

use App\Models\Auth\CodeExchangeRequest;
use App\Models\Auth\CodeExchangeResponse;
use App\Models\User;
use ErrorException;

class GoogleAuthService
{
    public function __construct(
        private GoogleAuthClient $google_client,
        private JwtDecoder $jwt_decoder,
        private StringUtils $string_utils
    ) {
    }

    public function exchange(CodeExchangeRequest $request): CodeExchangeResponse
    {
        $response = $this->google_client->exchange($request);
        $jwt = $this->jwt_decoder->decode($response->id_token);
        $email = $jwt->claims['email'];
        if ($this->string_utils->is_empty($email)) {
            throw new ErrorException("Email is not provided");
        }
        $user = User::firstOrNew(
            ['email' => $email],
            ['email' => $email]
        );
        $user->access_token = $response->access_token;
        $user->refresh_token = $response->refresh_token;
        $user->expires_in = $response->expires_in;
        $user->expires_at = time() + $user->expires_in;
        $user->save();

        return $response;
    }

    /**
     * Refresh a token for user with given email. 
     */
    public function refresh(string $email): CodeExchangeResponse
    {
        $user = User::where('email', $email)->first();
        if (is_null($user)) {
            throw new ErrorException("No user with given email");
        }
        $response = $this->google_client->refresh($user->refresh_token);
        $user->access_token = $response->access_token;
        $user->refresh_token = $response->refresh_token;
        $user->expires_in = $response->expires_in;
        $user->expires_at = time() + $user->expires_in;
        $user->save();

        return $response;
    }
}