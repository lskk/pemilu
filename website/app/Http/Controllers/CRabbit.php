<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Input;

use PhpAmqpLib\Connection\AMQPStreamConnection;
use PhpAmqpLib\Message\AMQPMessage;

use App\Models\WilayahModel;



use App\kandidat;
use App\Http\Controllers\Controller;

class CRabbit extends Controller
{
	protected $wilayah;

	public function __construct(WilayahModel $wilayah){

		$this->wilayah = $wilayah;
	}




    /*function index(){
	  $url = '167.205.7.229';
	  $port = 5672;
	  $user = 'pemiluuser';
	  $password = 'pemiluuser';
	  $virtual_host = '/pemilu';

	  //koneksi ke server rabbitmq
      $connection = new AMQPStreamConnection($url, $port, $user, $password, $virtual_host);
      $channel = $connection->channel();

	  //deklarasi antrian yang digunakan
	  $queue_name = 'hello';
      $channel->queue_declare($queue_name,  //queue name
								false,  //passive: false
								false,  //durable: false, if true the queue will survive if server restarts
								false,  //exclusive: false // the queue can be accessed in other channels
								false   //auto_delete: false //the queue won't be deleted once the channel is closed.
								);

	  //publish pesan ke antrian
	  $nama = 'nabila';
      $msg = new AMQPMessage($nama);
      $channel->basic_publish($msg,     //message
								'',     //exchange name
								'hello' //routing key
							 );

	  echo "[x] Sent $nama \n";

      //echo " [x] Sent 'nabila'\n";

      $channel->close();
      $connection->close();

    }*/

	function receive(){
		$url = '167.205.7.229';
        $port = 5672;
		$user = 'pemiluuser';
		$password = 'pemiluuser';
		$virtual_host = '/pemilu';

		$connection = new AMQPStreamConnection($url, $port, $user, $password, $virtual_host);
		$channel = $connection->channel();

		/*deklarasi antrian yang digunakan*/
		$queue_name = 'hello';
		$channel->queue_declare($queue_name,  //queue name
								false,  //passive: false
								false,  //durable: false, if true the queue will survive if server restarts
								false,  //exclusive: false // the queue can be accessed in other channels
								false   //auto_delete: false //the queue won't be deleted once the channel is closed.
								);
		/*end*/

		echo ' [*] Waiting for messages. To exit press CTRL+C', "\n";

		/*
		$callback = function($msg) {
		   echo " [x] Received ", $msg->body, "\n";
		};

		$channel->basic_consume('hello', '', false, false, false, false, $callback);


		while(count($channel->callbacks)) {
			$channel->wait();
		}
		*/

		/*consume pesan dari antrian*/
		$receive = $channel->basic_get($queue_name);
		$channel->basic_ack($receive->delivery_info['delivery_tag']); //sent keterangan(acknowladgement)
																	  //message sudah diterima

		echo " [x] Received ", $receive->body, "\n";
		/*end*/

		$channel->close();
        $connection->close();
	}



	function getFormView(){
		 $kandidat = kandidat::all();
		 //return view('formsms', ['kandidat' => $kandidat]);

		$provinces = $this->wilayah->where('tingkat',1)->get();
//		$data['provinces'] = $this->wilayah->where('tingkat',1)->get();

		//return view("formsms",$data);
		return view("formsms", compact('provinces', 'kandidat'));
	}

	function wilayahChild($id){
		$child = $this->wilayah->find($id)->child;

		return json_encode($child);
	}

	function postForm(){
	  $url = '167.205.7.229';
	  $port = 5672;
	  $user = 'pemiluuser';
	  $password = 'pemiluuser';
	  $virtual_host = '/pemilu';

	  //koneksi ke server rabbitmq
      $connection = new AMQPStreamConnection($url, $port, $user, $password, $virtual_host);
      $channel = $connection->channel();

	  //deklarasi antrian yang digunakan
	  $queue_name = 'hello';
      $channel->queue_declare($queue_name,  //queue name
								false,  //passive: false
								false,  //durable: false, if true the queue will survive if server restarts
								false,  //exclusive: false // the queue can be accessed in other channels
								false   //auto_delete: false //the queue won't be deleted once the channel is closed.
								);

		//sms
		$tps = Input::get('tps');
		$provinsi = Input::get('provinsi');
		$kabupaten = Input::get('kabupaten');
		$kecamatan = Input::get('kecamatan');
		$desa = Input::get('desa');
		$kd1 = Input::get('kd1');
		$kd2 = Input::get('kd2');
		$kd3 = Input::get('kd3');
		/*$lng_Prov = Input::get('lng_Prov');
		$lat_Prov = Input::get('lat_Prov');
		$lng_Kab = Input::get('lng_Kab');
		$lat_Kab = Input::get('lat_Kab');
		$lng_Kec = Input::get('lng_Kec');
		$lat_Kec = Input::get('lat_Kec');
		$lng_Desa = Input::get('lng_Desa');
		$lat_Desa = Input::get('lat_Desa');*/

	 //publish pesan ke antrian
	  $nama = $tps;
      $message = new AMQPMessage($nama);
      $channel->basic_publish($message,     //message
								'',     //exchange name
								'hello' //routing key
							 );

	  echo "[x] Sent $nama \n";
	//	echo "<script>alert(sent $nama);window.history.go(-1);</script>";
//-------------------------------------------------------------------------------------------------------------------------------------------------------
	  //sms

	  	//$result = DB::insert('insert into outbox (DestinationNumber, TextDecoded, CreatorID) values (?, ?, ?)', [$noTujuan, $message, 'Gammu']);
		//$result = DB::insert('insert into outbox (nohp, message) values (?, ?)', [$noTujuan, $message]);

		//mysql_connect("localhost", "root", "");
		//mysql_select_db("test");

		//$query = DB::insert('insert into polling (ID_DataTPS, provinsi, kabupaten, kecamatan, desa, Kan1, Kan2, Kan3, lng_Prov, lat_Prov, lng_Kab, lat_Kab, lng_Kec, lat_Kec, lng_Desa, lat_Desa) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)', [$tps, $provinsi, $kabupaten, $kecamatan, $desa, $kd1, $kd2, $kd3, $lng_Prov, $lat_Prov, $lng_Kab, $lat_Kab, $lng_Kec, $lat_Kec, $lng_Desa, $lat_Desa]);
		$query = DB::insert('insert into polling (ID_DataTPS, provinsi, kabupaten, kecamatan, desa, Kan1, Kan2, Kan3) values (?, ?, ?, ?, ?, ?, ?, ?)', [$tps, $provinsi, $kabupaten, $kecamatan, $desa, $kd1, $kd2, $kd3]);
		//$query="INSERT INTO message (tps, message) VALUES ('$_POST[tps]','$_POST[message]')";

			if($query){
				$message = "sms berhasil";
			}else{
				$message = "sms gagal";
			}

			return view('hasilForm', compact('message'));

      //echo " [x] Sent 'nabila'\n";
//----------------------------------------------------------------------------------------------------------------------------------------------------------


      $channel->close();
      $connection->close();

    }
}


?>
