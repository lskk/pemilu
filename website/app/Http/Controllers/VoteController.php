<?php

namespace App\Http\Controllers;

use App\Http\Requests;
use Illuminate\Http\Request;
use Auth;

use App\Models\WilayahModel;

use App\kandidat;
use App\kandidat_walkot;


use App\Polling;
use App\PollingLikeUnlike;
use App\UserPoin;
use Illuminate\Support\Facades\Input;

class VoteController extends Controller
{

    protected $wilayah;

    public function __construct(WilayahModel $wilayah){
        $this->wilayah = $wilayah;
        $this->middleware('auth');

    }


    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\Http\Response
     */
    public function add($type)
    {
        $kandidat = kandidat::all();
       // $kandidat_walkot = kandidat_walkot::all();
        $provinces = $this->wilayah->where('tingkat',1)->get();
        $cities = $this->wilayah->where('tingkat',2)->get();
        $districts = $this->wilayah->where('tingkat',3)->get();
        $villages = $this->wilayah->where('tingkat',4)->get();
        return view('vote/add', compact('provinces','cities','districts','villages','kandidat','kandidat_walkot','type')); 
    }


    public function save(Request $request)
    {

    /* RABBITMQ
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


*/
    //---------------------------------------------------------------------------------


        if(Input::hasfile('c1photo')){
           $image = Input::file('c1photo');
           $upload = base_path().'/public/formulir/';
           $filename = $request->tps.'-'.Auth::user()->id.'.jpg';
            $image->move($upload, $filename);
        }
        $polling = new Polling;
        $polling->ID_DataTPS = $request->tps;
        $polling->provinsi = $request->provinsi;
        $polling->kabupaten = $request->kabupaten;
        $polling->kecamatan = $request->kecamatan;
        $polling->desa = $request->desa;
        $polling->Kan1 = $request->kd1;
        $polling->Kan2 = $request->kd2;
        $polling->Kan3 = $request->kd3;
        $polling->user_id = Auth::user()->id;
        $polling->photo_c1 = $filename;
        $polling->type = $request->type;
        
        if($polling->save()) {
            $userPoin = new UserPoin;
            $userPoin->user_id = Auth::user()->id;
            $userPoin->poin_source_id = $polling->id;
            $userPoin->poin = 10;
            $userPoin->save();

/*-------------------------------------------------------------------------
             //publish pesan ke antrian
      $nama = $polling;
      $message = new AMQPMessage($nama);
      $channel->basic_publish($message,     //message
                                '',     //exchange name
                                'hello' //routing key
                             );
-------------------------------------------------------------------*/
            return redirect('report');   

/*--------------------------------------------

      $channel->close();
      $connection->close();
-------------------------------------    */
        }

    }

    public function likeUnlike($type, $pollingId) {
        $userId = Auth::user()->id;
        $likeUnlike = new PollingLikeUnlike;
        
        $likeUnlikeCurrent = PollingLikeUnlike::where('user_id', $userId)->where('polling_id', $pollingId);
        $likeUnlikeCurrentIsExist = $likeUnlikeCurrent->exists();
        if($likeUnlikeCurrentIsExist) {
            $likeUnlikeCurrentData = $likeUnlikeCurrent->first();
            $likeUnlike = PollingLikeUnlike::where('polling_likeunlike_id', $likeUnlikeCurrentData->polling_likeunlike_id);
            
            if($type=="like") {
                $likeUnlike->update([
                    'likes' => 1,
                    'unlikes' => 0
                ]);
            } else{ 
                $likeUnlike->update([
                    'likes' => 0,
                    'unlikes' => 1
                ]);
            }        
        } else {
            $likeUnlike->user_id = $userId;
            $likeUnlike->polling_id = $pollingId;
            
            $likeUnlike = $this->likeUnlikeRow($type, $likeUnlike);
        
            $likeUnlike->save();

            //tambah poin kalo cuman baru like atau unlike aja
            $userPoin = new UserPoin;
            $userPoin->user_id = Auth::user()->id;
            $userPoin->poin_source_id = $likeUnlike->id;
            $userPoin->poin = 1;
            $userPoin->save();
        }



        return redirect('report');
    }

    public function likeUnlikeRow($type, $likeUnlike) {
        if($type=="like") {
            $likeUnlike->unlikes = 0;
            $likeUnlike->likes = 1;
        } else{ 
            $likeUnlike->unlikes = 1;
            $likeUnlike->likes = 0;
        }
        return $likeUnlike;
    }
}
