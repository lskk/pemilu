@extends('layouts.app')
@section('content')

<!--<body style='background-image:url({{asset("image/bg.jpg")}}); background-size:100%; '>-->



<div class="container">
    <div class="row">
        <div class="col-md-11 col-md-offset-1">
          <form method="GET" action=""  class="form-inline">

            <select name="provinsi" class="form-control validate[required,custom[onlyLetter],length[0,100]] feedback-input provinsi">
              <option value=""> -Provinsi- </option>
              @foreach($provinces as $province)
              <option @if($province->wilayah_id==app('request')->input('provinsi')) selected="selected" @endif value="{{ $province->wilayah_id }}">{{ $province->nama }}</option>
              @endforeach
            </select>
            
            <select name="kabupaten" class="form-control validate[required,custom[onlyLetter],length[0,100]] feedback-input kabupaten">
              <option value=""> -Kabupaten/Kota- </option>
              @foreach($cities as $city)
              <option @if($city->wilayah_id==app('request')->input('kabupaten')) selected="selected" @endif value="{{ $city->wilayah_id }}">{{ $city->nama }}</option>
              @endforeach
            </select>
            
          
            
            <select name="kecamatan" class="form-control validate[required,custom[onlyLetter],length[0,100]] feedback-input kecamatan">
              <option value=""> -Kecamatan- </option>
               @foreach($districts as $district)
              <option @if($district->wilayah_id==app('request')->input('kecamatan')) selected="selected" @endif value="{{ $district->wilayah_id }}">{{ $district->nama }}</option>
              @endforeach
            </select>
           
          
            
            <select name="desa" class="form-control validate[required,custom[onlyLetter],length[0,100]] feedback-input desa">
              <option value=""> -Kelurahan/Desa- </option>
               @foreach($villages as $village)
              <option @if($village->wilayah_id==app('request')->input('desa')) selected="selected" @endif value="{{ $village->wilayah_id }}">{{ $village->nama }}</option>
              @endforeach
            </select>

            <select name="TPS" class="form-control validate[required,custom[onlyLetter],length[0,100]] feedback-input desa">
              <option value=""> -Pilih TPS- </option>
              @foreach($tpses as $tps)
              <option @if($tps->ID_DataTPS==app('request')->input('TPS')) selected="selected" @endif value="{{ $tps->ID_DataTPS }}">TPS {{ $tps->ID_DataTPS }}</option>
              @endforeach
            </select>

            <select name="type" class="form-control validate[required,custom[onlyLetter],length[0,100]] feedback-input desa">
              <option value=""> -Pilih Tipe- </option>
          <!--    <option value="walkot" @if("walkot"==app('request')->input('type')) selected="selected" @endif> Walikota </option> -->
              <option value="gubernur" @if("gubernur"==app('request')->input('type')) selected="selected" @endif> Gubernur </option>
            </select>


            
            <input class="form-control btn btn-primary" type="submit" name="submit" value="Filter">
          </form>
        </div>
        <div class="col-md-11 col-md-offset-1">
            <h3>Timeline Polling</h3>
            <h6>Daftar Inputan User</h6>
            <hr/>
            <div class="row">
            
            @if(count($polling)==0) 
              <p style="color: maroon; font-style: italic; text-align: center;"> Belum Ada Inputan User </p>
            @endif

            @foreach($polling as $poll)
            <div class="col-md-2">
              <img class="timeline-userpict" src="{{asset('fotoprofil/'.$poll->photo)}}" width="100%">
              <p class="timeline-username">
              {{$poll->name}}
              </p>


            </div>
            
            <div class="timeline-poll col-md-10">
              <div class="col-md-4">
                <h3>
                  Hasil Suara (TPS {{$poll->ID_DataTPS}})
                </h3>
                <p>
                  Kandidat 1 : <b>{{$poll->Kan1}}</b>
                </p>
                <p>
                  Kandidat 2 : <b>{{$poll->Kan2}}</b>
                </p>
                <p>
                  Kandidat 3 : <b>{{$poll->Kan3}}</b>
                </p>
              </div>
              <div>
                <h3>
                  Formulir C1
                </h3>
                <img src="{{asset('formulir/'.$poll->photo_c1)}}" width="100px">
              </div>
              <div style="clear: both"></div>
              @php
                $likeUnlikeData = $pollingLike::where('polling_id', $poll->ID);
                $likes = $likeUnlikeData->sum('likes');
                $unLikes = $likeUnlikeData->sum('unlikes');
                $isYouUnlikeIt = 0;
                $isYouLikeIt = 0;

                if(!Auth::guest()) {
                  $isYouUnlikeIt = $pollingLike::where('polling_id', $poll->ID)->where('user_id', Auth::user()->id)->where('unlikes', 1)->exists();
                  $isYouLikeIt = $pollingLike::where('polling_id', $poll->ID)->where('user_id', Auth::user()->id)->where('likes', 1)->exists();
                }
                
              @endphp
              <div class="timeline-likes" style="margin-top:20px">
                <div class="timeline-like col-md-2">
                  <a class="@if($isYouLikeIt) liked @endif" href="@if(!Auth::guest()) vote/like/{{$poll->ID}} @else # @endif" @if(Auth::guest()) onclick="alert('Anda harus login terlebih dahulu')" @endif> {{$likes}} Valid </a>
                </div>
                <div class="timeline-unlike col-md-2">
                  <a class="@if($isYouUnlikeIt) unliked @endif" href="@if(!Auth::guest()) vote/unlike/{{$poll->ID}} @else # @endif" @if(Auth::guest()) onclick="alert('Anda harus login terlebih dahulu')" @endif> {{$unLikes}} Unvalid</a>
                </div>
                <div class="timeline-share col-md-2">
                  <div class="fb-share-button" data-href="http://abdymalikmulky.com?id={{$poll->ID}}" data-layout="button" data-size="small" data-mobile-iframe="true"><a class="fb-xfbml-parse-ignore" target="_blank" href="https://www.facebook.com/sharer/sharer.php?u=https%3A%2F%2Fdevelopers.facebook.com%2Fdocs%2Fplugins%2F&amp;src=sdkpreparse">Share</a></div>
                </div>
                @if (Auth::guest())
                <div class="timeline-login col-md-4">
                  | <a href="/login"> Login </a> terlebih dahulu
                </div>
                @endif
              </div>

            </div>
            <div style="clear: both"></div>
            <hr/>
            @endforeach
            </div>
            
        </div>
    </div>
</div>
@endsection
