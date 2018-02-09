<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/


// Route::get('/formsms', 'CRabbit@getFormView');
// Route::post('/send1', ['as'=>'Send','uses'=>'CRabbit@postForm']);

// Route::get('/index', 'OSMController@getIndexView');
// Route::post('/indexPost', ['as'=>'postIndex' ,'uses'=>'OSMController@postAjax']);

// /* ----- ajax -----*/
// Route::get('/getChildWilayah/{id}', 'CRabbit@wilayahChild');


// Route::get('/formWalkot', 'CRabbit_Walkot@getFormView');
// Route::post('/sendwalkot', ['as'=>'Sendwalkot','uses'=>'CRabbit_Walkot@postForm']);


// // Crawler OPEN GOV
// Route::get('/kpu', 'KPUController@index');
// Route::post('/kpu/fetch', 'KPUController@fetch');



Route::auth();
//Route::get('/register','RegisterController@index');


Route::get('/home', 'HomeController@index');
Route::get('/', 'HomeController@index');

Route::get('/vote/{type}', 'VoteController@add');
Route::get('/vote/{type}/{pollId}', 'VoteController@likeUnlike');
Route::post('/vote/save', 'VoteController@save');
Route::get('/vote', 'VoteController@kandidat');
//Route::get('/vote', 'VoteController@kandidat_walkot');


Route::get('/report', 'ReportController@index');
//Route::get('/rules', 'VoteController@show');

//------------------

//Route::get('/receive', 'VoteController@receive');


//Route::get('maps', 'MapsController@show');

Route::get('rules', 'RulesController@users');
 

