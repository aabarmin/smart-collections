<?php

namespace Tests\Unit;

use App\Models\Auth\Jwt;
use App\Services\JwtValidator;
use PHPUnit\Framework\TestCase;

class JwtValidatorTest extends TestCase
{
    public function test_no_claims(): void
    {
        $validator = new JwtValidator();
        $jwt = new Jwt([], []);

        $this->assertFalse($validator->is_valid($jwt));
    }

    public function test_wrong_issuer(): void
    {
        $validator = new JwtValidator();
        $jwt = new Jwt([], [
            'iss' => 'abc'
        ]);

        $this->assertFalse($validator->is_valid($jwt));
    }

    public function test_expired(): void
    {
        $validator = new JwtValidator();
        $jwt = new Jwt([], [
            'iss' => 'https://accounts.google.com',
            'exp' => time() - 100
        ]);

        $this->assertFalse($validator->is_valid($jwt));
    }

    public function test_email(): void
    {
        $validator = new JwtValidator();
        $jwt = new Jwt([], [
            'iss' => 'https://accounts.google.com',
            'exp' => time() + 10
        ]);

        $this->assertFalse($validator->is_valid($jwt));
    }

    public function test_positive(): void
    {
        $validator = new JwtValidator();
        $jwt = new Jwt([], [
            'iss' => 'https://accounts.google.com',
            'exp' => time() + 10,
            'email' => 'test@email.com'
        ]);

        $this->assertTrue($validator->is_valid($jwt));
    }
}