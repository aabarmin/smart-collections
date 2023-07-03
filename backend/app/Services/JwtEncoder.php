<?php

namespace App\Services;

use App\Models\Auth\Jwt;

class JwtEncoder
{
    public function encode(Jwt $jwt): string
    {
        $parts = [];
        array_push($parts, $this->to_base64($jwt->headers));
        array_push($parts, $this->to_base64($jwt->claims));
        array_push($parts, '');

        return implode(".", $parts);
    }

    private function to_base64(array $part): string
    {
        $as_string = json_encode($part);
        return base64_encode($as_string);
    }
}