@extends('layouts.app')

@section('content')
    <nav class="navbar bg-light">
        <div class="container">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="{{ url("/library/{$vinyl->vinyl_id}") }}">
                        Back
                    </a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <div class="row">
            <div class="col">
                <input type="file" style="display: none" id="file-upload" name="file" />

                <form method="POST" action="/library/{{ $vinyl->vinyl_id }}">
                    @csrf

                    <h2>Vinyl Details</h2>

                    <div class="form-floating mb-3">
                        <input name="vinyl_title" type="text" class="form-control" id="album-title"
                            value="{{ $vinyl->vinyl_title }}">
                        <label for="album-title">Album Title</label>
                    </div>

                    <div class="form-floating mb-3">
                        <input name="vinyl_artist" type="text" class="form-control" id="album-artist"
                            value="{{ $vinyl->vinyl_artist }}">
                        <label for="album-artist">Album Artist</label>
                    </div>

                    <h2>Vinyl Images</h2>

                    <div class="container">
                        <div class="row row-cols-2 image-container">
                            @foreach ($vinyl->images as $vinyl_image)
                                <img src="{{ $vinyl_image->image_path }}" class="img-thumbnail" />
                                <input type="hidden" name="vinyl_images[{{ $loop->index }}][image_path]"
                                    value="{{ $vinyl_image->image_path }}" />
                            @endforeach
                        </div>
                    </div>

                    <div class="d-grid gap-2">
                        <button type="button" class="btn-lg btn btn-primary add-image-button">Add image</button>
                    </div>

                    <hr />

                    <h2>Vinyl Tracks</h2>

                    @foreach ($vinyl->sides as $vinyl_side)
                        <section>
                            <div class="form-floating mb-3">
                                <input name="vinyl_sides[{{ $vinyl_side->side_id }}][side_title]" type="text"
                                    class="form-control" id="side-{{ $vinyl_side->side_id }}-title"
                                    value="{{ $vinyl_side->side_title }}" />
                                <label for="side-{{ $vinyl_side->side_id }}-title">Side Title</label>
                            </div>

                            <div class="tracks-container" id="tracks-container-{{ $vinyl_side->side_id }}">
                                @foreach ($vinyl_side->tracks as $vinyl_track)
                                    <div class="form-floating mb-3 track-container input-group"
                                        id="side-{{ $vinyl_side->side_id }}-track-{{ $loop->index }}-container"
                                        data-track-id="{{ $loop->index }}">
                                        <input
                                            name="vinyl_sides[{{ $vinyl_side->side_id }}][side_tracks][{{ $loop->index }}][track_title]"
                                            type="text" class="form-control"
                                            id="side-{{ $vinyl_side->side_id }}-track-{{ $loop->index }}"
                                            value="{{ $vinyl_track->track_title }}" />
                                        <button type="button" data-side-id="{{ $vinyl_side->side_id }}"
                                            data-track-id="{{ $loop->index }}"
                                            class="remove-track-button btn btn-outline-secondary">
                                            X
                                        </button>
                                        <label for="side-{{ $vinyl_side->side_id }}-track-{{ $loop->index }}">
                                            Track name
                                        </label>
                                    </div>
                                @endforeach
                            </div>

                            <div class="d-grid gap-2">
                                <button type="button" data-side-id="{{ $vinyl_side->side_id }}"
                                    class="btn-lg btn btn-primary add-track-button">Add track</button>
                            </div>
                        </section>

                        <hr />
                    @endforeach

                    <div class="d-grid gap-2">
                        <button type="submit" class="btn-lg btn btn-primary">Save</button>
                    </div>
                </form>

            </div>
        </div>
    </div>

    <script type="module">
        $(document).ready(function(){
            function removeHandler(event) {
                const sideId = $(event.target).data("sideId");
                const trackId = $(event.target).data("trackId");
                const id = `#side-${sideId}-track-${trackId}-container`;

                $(id).remove();
            }

            $("button.remove-track-button").click(removeHandler);

            $(".add-track-button").click(function(event) {
                const sideId = $(event.target).data("sideId");
                const container = $(`#tracks-container-${sideId}`);
                const numberOfTracks = container
                    .children()
                    .length;

                $(container).append(
                    $(document.createElement("div"))
                        .addClass("form-floating mb-3 track-container input-group")
                        .attr("data-track-id", numberOfTracks)
                        .attr("id", `side-${sideId}-track-${numberOfTracks}-container`)
                        .append(
                            $(document.createElement("input"))
                                .addClass("form-control")
                                .attr("type", "text")
                                .attr("id", `side-${sideId}-track-${numberOfTracks}`)
                                .attr("name", `vinyl_sides[${sideId}][side_tracks][${numberOfTracks}][track_title]`)
                                .attr("placeholder", "Track name")
                        )
                        .append(
                            $(document.createElement("button"))
                                .addClass("btn btn-outline-secondary")
                                .addClass("remove-track-button")
                                .attr("type", "button")
                                .attr("data-track-id", numberOfTracks)
                                .attr("data-side-id", sideId)
                                .text("X")
                                .click(removeHandler)
                        )
                        .append(
                            $(document.createElement("label"))
                                .attr("for", `side-${sideId}-track-${numberOfTracks}`)
                                .text("Track name")
                        )
                );

                return false;
            });

            $(".add-image-button").click(function() {
                $("#file-upload").click();
            });

            $("#file-upload").change(function(event) {
                if (!event.target.files || event.target.files.length == 0) {
                    return;
                }
                const file = event.target.files[0];
                const formData = new FormData();
                formData.append("file", file);

                axios
                    .post("/api/files", formData, {
                        "Content-Type": "multipart/form-data"
                    })
                    .then(response => {
                        const created = response.headers.location;
                        const images = $(".image-container")
                            .children()
                            .length;

                        $(".image-container")
                            .append(
                                $(document.createElement("img"))
                                    .addClass("img-thumbnail")
                                    .attr("src", created)
                            )
                            .append(
                                $(document.createElement("input"))
                                    .attr("type", "hidden")
                                    .attr("name", `vinyl_images[${images}][image_path]`)
                                    .attr("value", created)
                            )
                    })
            });
        });
    </script>
@endsection
