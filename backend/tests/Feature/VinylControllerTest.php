<?php

namespace Tests\Feature;

use App\Http\Resources\VinylResource;
use App\Models\Vinyl;
use App\Models\VinylImage;
use Database\Factories\UserFactory;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Foundation\Testing\WithFaker;
use Tests\CreatesApplication;
use Tests\TestCase;

class VinylControllerTest extends TestCase
{
    use CreatesApplication;

    public function test_find_all(): void
    {
        $response = $this->get('/api/vinyls');

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
        $response = $this->postJson('/api/vinyls', $request);
        $response->assertStatus(201);
        $response->assertHeader('Location');

        $location = $response->headers->get('Location');
    }
}