<?php

namespace App\Services;

use App\Models\Auth\Jwt;

class JwtValidator
{
    public function is_valid(Jwt $token): bool
    {
        if (count($token->claims) == 0) {
            return false;
        }
        if ($token->claim_or_default('iss') != 'https://accounts.google.com') {
            return false;
        }
        if ($token->claim_or_default('exp', 0) < time()) {
            return false;
        }
        if ($token->claim_or_default('email') == '') {
            return false;
        }
        return true;
    }
}