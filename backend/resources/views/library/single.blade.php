@extends('layouts.app')

@section('content')
    <nav class="navbar bg-light">
        <div class="container">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="{{ url('/library') }}">
                        Back
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="{{ url("/library/{$vinyl->vinyl_id}/edit") }}">
                        Edit
                    </a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <div class="row">
            <div class="col">

                <div class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-inner">
                        @foreach ($vinyl->images as $vinyl_image)
                            <div @class(['carousel-item' => true, 'active' => $loop->first])>
                                <img src="{{ $vinyl_image->image_path }}" class="d-block w-100" />
                            </div>
                        @endforeach
                    </div>
                </div>

                <h2>{{ $vinyl->vinyl_title }}</h2>
                <small>by {{ $vinyl->vinyl_artist }}</small>

                @foreach ($vinyl->sides as $vinyl_side)
                    <section>
                        <h2>{{ $vinyl_side->side_title }}</h2>
                        <ul>
                            @foreach ($vinyl_side->tracks as $track)
                                <li>{{ $track->track_title }}</li>
                            @endforeach
                        </ul>
                    </section>
                @endforeach
            </div>
        </div>
    </div>
@endsection
