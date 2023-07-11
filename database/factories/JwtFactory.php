<?php

namespace Database\Factories;

use App\Models\Auth\Jwt;
use App\Models\User;


class JwtFactory
{
    public static function make(User $user): Jwt
    {
        return new Jwt(
            [
                'typ' => 'jwt',
                'alg' => 'none'
            ],
            [
                'iss' => 'https://accounts.google.com',
                'exp' => time() + 3600,
                'iat' => time(),
                'email' => $user->email
            ]
        );
    }
}