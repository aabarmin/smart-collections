<?php

namespace App\Exceptions;

use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Response;
use Symfony\Component\HttpKernel\Exception\HttpException;

class NotAuthenticatedException extends HttpException
{
    public function __construct(string $message)
    {
        parent::__construct(401, $message);
    }

    public function render(Request $request): JsonResponse
    {
        return Response::json([
            'error' => $this->message
        ], 401);
    }
}