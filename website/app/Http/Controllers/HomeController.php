<?php

namespace App\Http\Controllers;

use App\Http\Requests;
use Illuminate\Http\Request;
use Auth;

use App\UserPoin;
use DB;
class HomeController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('auth');
    }

    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\Http\Response
     */
    public function index(Request $request)
    {
        $usersPoin = UserPoin::join('users', 'users.id', '=', 'user_poin.user_id')->select(DB::raw('users.*'),DB::raw('SUM(poin) as total_poin'))->groupBy('users.id')->orderBy('total_poin', 'desc')->get();

        
        return view('home', compact('usersPoin'));

    }



}
