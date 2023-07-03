<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\User>
 */
class UserFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        $expires_in = fake()->randomNumber(3);

        return [
            'email' => fake()->unique()->safeEmail(),
            'access_token' => fake()->randomAscii(),
            'refresh_token' => fake()->randomAscii(),
            'expires_in' => $expires_in,
            'expires_at' => time() + $expires_in
        ];
    }
}