<?php

	namespace App\Http\Controllers;
	
	use Illuminate\Support\Facades\DB;
	use Illuminate\Support\Facades\Input;

	class OSMController extends controller{
		
		public function getIndexView(){
			return view('mainpage/indexmap');
		}
		
		public function postAjax(){
			$ids = Input::get('ids');
			//query sesuai id
			//yang mau ditampilin
			return view('mainpage/indexdetail', compact('ids'));
		}
		
	}

?>