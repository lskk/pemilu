<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Input;

use GuzzleHttp\Exception\GuzzleException;
use GuzzleHttp\Client;

use App\Models\WilayahModel;


use App\kandidat;
use App\Http\Controllers\Controller;



class KPUController extends Controller
{

	protected $wilayah;

	public function __construct(WilayahModel $wilayah){

		$this->wilayah = $wilayah;
	}



	function index(){
      
		$kandidat = kandidat::all();
		//return view('formsms', ['kandidat' => $kandidat]);

		$provinces = $this->wilayah->where('tingkat',1)->get();
		return view('kpu/index', compact('provinces', 'kandidat')); 

  }

  function fetch(Request $request){
  	$provinsi = $request->get('provinsi');
  	$kabupaten = $request->get('kabupaten');
  	$kecamatan = $request->get('kecamatan');
  	$desa = $request->get('desa');


	$kandidat = kandidat::all();
	//return view('formsms', ['kandidat' => $kandidat]);

	$provinces = $this->wilayah->where('tingkat',1)->get();

	$client = new \GuzzleHttp\Client();
	$resProvinsi = $client->request('GET', 'https://data.kpu.go.id/open/v1/api.php?cmd=pollingstation_browse&wilayah_id='.$provinsi);
	$bodyProvinsi = $resProvinsi->getBody();
	$bodyProvinsi = json_decode($bodyProvinsi);






	$pollingProvinsiName = $bodyProvinsi->wilayah; 
	$pollingProvinsi = $bodyProvinsi->data;

	return view('kpu/result', compact('provinsi','pollingProvinsiName','kabupaten','kecamatan','desa','pollingProvinsi','provinces', 'kandidat')); 
  }

}
