<?php

namespace App\Services;

use ErrorException;

class StringUtils
{
    public function is_empty(mixed $value): bool
    {
        if (is_null($value)) {
            return true;
        }
        if (!is_string($value)) {
            throw new ErrorException("Value is not a string");
        }
        if (strlen($value) == 0) {
            return true;
        }
        return false;
    }

    public function starts_with(string $string, string $prefix): bool
    {
        return str_starts_with($string, $prefix);
    }

    public function substring_after(string $string, string $prefix): string
    {
        $parts = explode($prefix, $string);
        return end($parts);
    }
}