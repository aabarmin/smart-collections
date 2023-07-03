<?php

namespace App\Services;

use App\Models\Auth\CodeExchangeRequest;
use App\Models\Auth\CodeExchangeResponse;
use Http;

class GoogleAuthClient
{
    private const BASE_URL = "https://oauth2.googleapis.com/token";
    private const CODE_GRANT_TYPE = "authorization_code";
    private const REFRESH_GRANT_TYPE = "refresh_token";

    public function __construct(
        private string $client_id,
        private string $client_secret,
        private string $redirect_uri
    ) {

    }

    public function exchange(CodeExchangeRequest $request): CodeExchangeResponse
    {
        $service_response = Http::asForm()->post(GoogleAuthClient::BASE_URL, [
            'code' => $request->code,
            'client_id' => $this->client_id,
            'client_secret' => $this->client_secret,
            'redirect_uri' => $this->redirect_uri,
            'grant_type' => GoogleAuthClient::CODE_GRANT_TYPE
        ]);

        if (!$service_response->ok()) {
            throw new \ErrorException("Can't perform token exchange");
        }

        $response_body = $service_response->json();
        return new CodeExchangeResponse(
            $response_body['access_token'],
            $response_body['refresh_token'],
            $response_body['id_token'],
            $response_body['token_type'],
            $response_body['expires_in']
        );
    }

    public function refresh(string $refresh_token): CodeExchangeResponse
    {
        $service_response = Http::asForm()->post(GoogleAuthClient::BASE_URL, [
            'refresh_token' => $refresh_token,
            'client_id' => $this->client_id,
            'client_secret' => $this->client_secret,
            'grant_type' => GoogleAuthClient::REFRESH_GRANT_TYPE
        ]);

        if (!$service_response->ok()) {
            throw new \ErrorException("Can't perform token exchange");
        }

        $response_body = $service_response->json();
        return new CodeExchangeResponse(
            $response_body['access_token'],
            $refresh_token,
            $response_body['id_token'],
            $response_body['token_type'],
            $response_body['expires_in']
        );
    }
}