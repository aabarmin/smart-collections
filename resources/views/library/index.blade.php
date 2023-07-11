@extends('layouts.app')

@section('content')
<nav class="navbar bg-light">
    <div class="container">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" data-bs-toggle="modal" data-bs-target="#add-vinyl-dialog">
                    Add vinyl
                </a>
            </li>
        </ul>
    </div>
</nav>

<div class="modal" id="add-vinyl-dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" action="/library">
                {{ csrf_field() }}
                <div class="modal-header">
                    <h1 class="modal-title fs-5">Add a vinyl</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="form-floating mb-3">
                        <input name="vinyl_title" type="text" class="form-control" id="album-title" placeholder="Around the world">
                        <label for="album-title">Album Title</label>
                    </div>

                    <div class="form-floating mb-3">
                        <input name="vinyl_artist" type="text" class="form-control" id="album-artist" placeholder="Daft Punk">
                        <label for="album-artist">Album Artist</label>
                    </div>

                    <div class="form-floating mb-3">
                        <select class="form-select" name="vinyl_disks">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                        </select>
                        <label for="album-disks">Number of disks</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">
                        Create
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="container py-4">
    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 row-cols-xl-4">
        @foreach ($records as $vinyl)
        <div class="col">
            <div class="card card-clickable" data-vinyl-id="{{ $vinyl->vinyl_id }}">
                @if (count($vinyl->images) > 0)
                <img src="{{ $vinyl->images()->first()->image_path }}" class="card-img-top" />
                @endif
                <div class="card-body">
                    <h5 class="card-title">{{ $vinyl->vinyl_title }}</h5>
                    <h6 class="card-subtitle mb-2 text-body-secondary">by {{ $vinyl->vinyl_artist }}</h6>
                </div>
            </div>
        </div>
        @endforeach
    </div>
</div>

<script type="module">
    $(document).ready(function() {
        $(".card-clickable").click(function(event) {
            const vinylId = $(event.currentTarget).data('vinylId');
            const url = `{{ url('/library') }}/${vinylId}`;
            window.location.href = url;
        });
    });
</script>
@endsection