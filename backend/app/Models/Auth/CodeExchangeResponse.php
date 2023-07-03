<?php

namespace App\Models\Auth;

class CodeExchangeResponse
{
    public function __construct(
        public string $access_token,
        public string $refresh_token,
        public string $id_token,
        public string $token_type,
        public int $expires_in,
    ) {
    }
}