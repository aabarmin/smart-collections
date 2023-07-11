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
        Schema::create('vinyl_sides', function (Blueprint $table) {
            $table->id('side_id')->autoIncrement();
            $table->string('side_title', 1024);
            $table->foreignId('vinyl_id')
                ->references('vinyl_id')
                ->on('vinyls')
                ->cascadeOnDelete()
                ->cascadeOnUpdate();
            $table->index('vinyl_id');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('vinyl_sides');
    }
};