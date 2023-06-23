<?php
    $zipArchive = new ZipArchive();
    $result = $zipArchive->open("./archive.zip");
    if ($result) {
        $zipArchive->extractTo(".");
        $zipArchive->close();
    }