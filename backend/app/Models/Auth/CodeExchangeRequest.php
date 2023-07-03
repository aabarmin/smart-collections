<?php

namespace App\Models\Auth;

class CodeExchangeRequest
{
    public $code;

    public static function create(array $props): CodeExchangeRequest
    {
        $request = new CodeExchangeRequest();
        $request->code = $props['code'];
        return $request;
    }
}