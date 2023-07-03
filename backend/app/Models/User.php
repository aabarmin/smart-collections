<?php

namespace App\Models;

use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Database\Eloquent\Factories\HasFactory;

class User extends Authenticatable
{
    use HasFactory;

    protected $primary_key = 'user_id';
    protected $fillable = [
        'email',
        'access_token',
        'refresh_token',
        'expires_in',
        'expires_at'
    ];
}