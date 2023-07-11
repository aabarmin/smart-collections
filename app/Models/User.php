<?php

namespace App\Models;

use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Database\Eloquent\Factories\HasFactory;

/**
 * @property int $id
 * @property string $user_email
 * @property string $user_name
 */
class User extends Authenticatable
{
    use HasFactory;

    protected $fillable = [
        'user_email',
        'user_name'
    ];
}
