<?php

namespace Tests\Feature;

use App\Http\Resources\VinylResource;
use App\Models\User;
use App\Models\Vinyl;
use App\Models\VinylImage;
use App\Services\JwtDecoder;
use App\Services\JwtEncoder;
use Database\Factories\JwtFactory;
use Database\Factories\UserFactory;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Foundation\Testing\WithFaker;
use Illuminate\Support\Facades\Auth;
use Tests\CreatesApplication;
use Tests\TestCase;

class VinylControllerTest extends TestCase
{
    use CreatesApplication;

    private $token;

    public function setUp(): void
    {
        parent::setUp();
        $user = User::factory()->create();
        $encoder = new JwtEncoder();
        $jwt = JwtFactory::make($user);
        $this->token = $encoder->encode($jwt);
    }

    public function test_find_all(): void
    {
        $response = $this->get('/api/vinyls', [
            'Authorization' => 'Bearer ' . $this->token
        ]);

        $response->assertStatus(200);
    }

    public function test_create_then_find(): void
    {
        $request = [
            'vinyl_title' => fake()->name(),
            'vinyl_artist' => fake()->name(),
            'vinyl_images' => [],
            'vinyl_sides' => []
        ];
        $response = $this->postJson('/api/vinyls', $request, [
            'Authorization' => 'Bearer ' . $this->token
        ]);
        $response->assertStatus(201);
        $response->assertHeader('Location');

        $location = $response->headers->get('Location');
    }
}