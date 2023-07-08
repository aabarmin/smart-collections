<?php

namespace App\Models;

use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Database\Eloquent\Factories\HasFactory;

/**
 * @property number $user_id
 * @property string $user_email
 * @property string $user_name
 */
class User extends Authenticatable
{
    use HasFactory;

    protected $primary_key = 'user_id';
    protected $fillable = [
        'user_email',
        'user_name'
    ];

    public function getAuthIdentifier()
    {
        return $this->user_id;
    }

    public function getAuthIdentifierName()
    {
        return $this->primary_key;
    }
}
