<?php

namespace App\Http\Controllers;

use App\Http\Requests;
use Illuminate\Http\Request;
use Auth;

use App\Models\WilayahModel;



use App\PollingLikeUnlike;
use App\Polling;
use App\User;

use DB;


class ReportController extends Controller
{
    protected $wilayah;
    public function __construct(WilayahModel $wilayah){
        $this->wilayah = $wilayah;
    }

    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\Http\Response
     */
    public function index(Request $request)
    {
        $provinces = $this->wilayah->where('tingkat',1)->get();
        $cities = $this->wilayah->where('tingkat',2)->get();
        $districts = $this->wilayah->where('tingkat',3)->get();
        $villages = $this->wilayah->where('tingkat',4)->get();
        $tpses = Polling::select(DB::raw("distinct ID_DataTPS"))->get();
        $pollingLike = new PollingLikeUnlike;

        $pollingQuery = Polling::join('users', 'users.id', '=', 'polling.user_id')->orderBy('polling.id', "desc");
        if ($request->has('provinsi')) {
            $pollingQuery->where('provinsi', $request->input('provinsi'));
        }
        if ($request->has('kabupaten')) {
            $pollingQuery->where('kabupaten', $request->input('kabupaten'));
        }
        if ($request->has('kecamatan')) {
            $pollingQuery->where('kecamatan', $request->input('kecamatan'));
        }
        if ($request->has('desa')) {
            $pollingQuery->where('desa', $request->input('desa'));
        }
        if ($request->has('type')) {
            $pollingQuery->where('type', $request->input('type'));
        }
        if ($request->has('TPS')) {
            $pollingQuery->where('ID_DataTPS', $request->input('TPS'));
        }



        $polling = $pollingQuery->get();

        return view('report/index', compact('provinces', 'cities', 'districts', 'villages', 'polling', 'pollingLike', 'tpses')); 
    }


}
