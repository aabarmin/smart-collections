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

    public function test_find_all(): void
    {
        $user = User::factory()->create();

        $request = [
            'vinyl_title' => fake()->name(),
            'vinyl_artist' => fake()->name(),
            'vinyl_disks' => fake()->numberBetween(1, 5)
        ];

        $this->actingAs($user)
            ->post('/library', $request)
            ->assertStatus(302);

        $this->actingAs($user)
            ->get('/library')
            ->assertStatus(200);
    }

    public function test_create_then_find(): void
    {
        $request = [
            'vinyl_title' => fake()->name(),
            'vinyl_artist' => fake()->name(),
            'vinyl_disks' => fake()->randomNumber(1, 5)
        ];
        $user = User::factory()->create();
        $this->actingAs($user)
            ->post('/library', $request)
            ->assertStatus(302);

        $vinyl = Vinyl::whereUser($user)->firstOrFail();
        $this->actingAs($user)
            ->get("/library/{$vinyl->vinyl_id}")
            ->assertStatus(200);
    }
}
