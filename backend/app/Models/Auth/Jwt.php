<?php

namespace App\Models\Auth;

class Jwt
{
    public function __construct(
        public array $headers,
        public array $claims
    ) {
    }

    public function claim_or_default(string $claim, string $default = ''): mixed
    {
        if (array_key_exists($claim, $this->claims)) {
            return $this->claims[$claim];
        }
        return $default;
    }
}