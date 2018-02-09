<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Input;

use PhpAmqpLib\Connection\AMQPStreamConnection;
use PhpAmqpLib\Message\AMQPMessage;

class CRabbit extends Controller
{
    
   /* function index(){
      $connection = new AMQPStreamConnection('167.205.7.226', 5672, 'pemiluuser', 'pemiluuser', '/pemilu');
      $channel = $connection->channel();

      $channel->queue_declare('hello', false, false, false, false);

      $msg = new AMQPMessage('hai');
      $channel->basic_publish($msg, '', 'hello');

	  $nama = 'hai';
	  echo "[x] Sent $nama \n";
	  
      //echo " [x] Sent 'nabila'\n";

      $channel->close();
      $connection->close();

    }*/
	function receive(){
		
		$connection = new AMQPStreamConnection('167.205.7.226', 5672, 'pemiluuser', 'pemiluuser', '/pemilu');
		$channel = $connection->channel();

		$channel->queue_declare('hello', false, false, false, false);

		echo ' [*] Waiting for messages. To exit press CTRL+C', "\n";

		$callback = function($msg) {
		echo " [x] Received ", $msg->body, "\n";
		};

		$channel->basic_consume('hello', '', false, true, false, false, $callback);

		while(count($channel->callbacks)) {
			$channel->wait();
		}
	}
	
	
	
	public function getFormView(){
			$nama = "Testing Laravel";
			return view("formsms");
			//return view("formsms", compact('nama'));
		}
		
	public function postForm(){ 
			
			$connection = new AMQPStreamConnection('167.205.7.226', 5672, 'pemiluuser', 'pemiluuser', '/pemilu');
			$channel = $connection->channel();

			$channel->queue_declare('hello', false, false, false, false);

			$msg = new AMQPMessage('Hello World');
			$channel->basic_publish($msg, '', 'hello');

			$noTujuan = Input::get('nohp'); 
			$message = Input::get('msg');
			
			
			echo "[x] Sent $message \n";
			  
			  
			  
			$result = DB::insert('insert into outbox (DestinationNumber, TextDecoded, CreatorID) values (?, ?, ?)', [$noTujuan, $message, 'Gammu']);
			//$result = DB::insert('insert into outbox (nohp, message) values (?, ?)', [$noTujuan, $message]);
			
			if($result){
				$msg = "sms berhasil";
			}else{
				$msg = "sms gagal";
			}
			
			return view('hasilForm', compact('msg')); 
			$channel->close();
			$connection->close();
			
		} 
	
	
}
