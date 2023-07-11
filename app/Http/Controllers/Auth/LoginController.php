<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Laravel\Socialite\Facades\Socialite;

class LoginController extends Controller
{
    public function login()
    {
        return view('auth/login');
    }

    public function loginWithGoogle()
    {
        return Socialite::driver('google')->redirect();
    }

    public function callback(Request $request)
    {
        $google_user = Socialite::driver('google')->user();

        $user = User::firstOrCreate([
            'user_email' => $google_user->getEmail()
        ], [
            'user_email' => $google_user->getEmail(),
            'user_name' => $google_user->getName()
        ]);

        Auth::login($user, true);

        return redirect('/library');
    }
}
