<?php

namespace App\Services;

use App\Models\Auth\Jwt;

class JwtDecoder
{
    public function decode(string $token): Jwt
    {
        $parts = explode(".", $token);
        $header = json_decode(base64_decode($parts[0]), true);
        $claims = json_decode(base64_decode($parts[1]), true);

        return new Jwt(
            $header,
            $claims
        );
    }
}