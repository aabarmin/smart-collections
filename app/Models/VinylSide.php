<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;

class VinylSide extends Model
{
    protected $primaryKey = 'side_id';
    protected $fillable = [
        'side_title',
        'vinyl_id'
    ];

    public function tracks(): HasMany
    {
        return $this->hasMany(VinylTrack::class, 'side_id');
    }
}