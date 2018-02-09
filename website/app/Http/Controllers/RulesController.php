<?php
namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\View;
use App\Models\users;



class RulesController extends Controller
{
    
    public function __construct(){
    }
    
   
    public function users(){
         $users = users::all();
        return view('rules', compact('users'));
    }
   

}
