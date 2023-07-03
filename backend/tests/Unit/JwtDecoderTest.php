<?php

namespace Tests\Unit;

use App\Services\JwtDecoder;
use PHPUnit\Framework\TestCase;

class JwtDecoderTest extends TestCase
{
    private const TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    // private $jwt_decoder = new JwtDecoder();

    public function test_read_string(): void
    {
        $jwt_decoder = new JwtDecoder();
        $jwt = $jwt_decoder->decode(JwtDecoderTest::TOKEN);

        $this->assertNotNull($jwt);

        $this->assertEquals($jwt->headers['alg'], "HS256");
        $this->assertEquals($jwt->headers['typ'], "JWT");

        $this->assertEquals($jwt->claims['sub'], "1234567890");
        $this->assertEquals($jwt->claims['name'], "John Doe");
    }
}