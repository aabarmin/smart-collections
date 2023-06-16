<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration {
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('vinyl_tracks', function (Blueprint $table) {
            $table->id('track_id')->autoIncrement();
            $table->foreignId('side_id')->references('side_id')->on('vinyl_sides');
            $table->string('track_title', 1024);
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('vinyl_tracks');
    }
};