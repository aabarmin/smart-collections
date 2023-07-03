<?php

namespace App\Http\Middleware;

use App\Exceptions\NotAuthenticatedException;
use App\Models\User;
use App\Services\JwtDecoder;
use App\Services\JwtValidator;
use App\Services\StringUtils;
use Closure;
use Exception;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Symfony\Component\HttpFoundation\Response;


class Authenticate
{
    public function __construct(
        private StringUtils $string_utils,
        private JwtValidator $jwt_validator,
        private JwtDecoder $jwt_decoder
    ) {
    }

    public function handle(Request $request, Closure $next): Response
    {
        $header = $request->header('Authorization');
        if ($this->string_utils->is_empty($header)) {
            throw new NotAuthenticatedException('No Authorization header in the request');
        }
        if (!$this->string_utils->starts_with($header, 'Bearer')) {
            throw new NotAuthenticatedException('Provided token is not of Bearer type');
        }
        $token_string = $this->string_utils->substring_after($header, 'Bearer ');
        try {
            $jwt = $this->jwt_decoder->decode($token_string);
        } catch (Exception $e) {
            throw new NotAuthenticatedException('Can\'t parse token');
        }
        if (!$this->jwt_validator->is_valid($jwt)) {
            throw new NotAuthenticatedException('Token is not valid');
        }
        $email = $jwt->claims['email'];
        $user = User::where('email', $email)->first();
        if (is_null($user)) {
            throw new NotAuthenticatedException('Token does not belong to the application');
        }
        Auth::login($user);

        return $next($request);
    }
}