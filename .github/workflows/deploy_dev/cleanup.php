<?php
    function recursive_remove($path) {
        if (file_exists($path) && is_dir($path)) {
            foreach (scandir($path) as $entry) {
                if (!in_array($entry, ['.', '..'], true)) {
                    recursive_remove($path . DIRECTORY_SEPARATOR . $entry);
                }
            }
            rmdir($path);
        } else if (file_exists($path)) {
            unlink($path);
        }
    }

    $to_remove = [
        "app",
        "bootstrap",
        "config",
        "database",
        "public",
        "resources",
        "routes",
        "vendor",
        ".env", 
        "archive.zip",
        "cleanup.php",
        "unpack.php", 
        "error_log"
    ];

    foreach ($to_remove as $item) {
        recursive_remove($item);
    }