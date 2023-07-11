<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class VinylTrack extends Model
{
    use HasFactory;

    protected $primaryKey = 'track_id';
    protected $fillable = [
        'track_title',
        'side_id'
    ];
}