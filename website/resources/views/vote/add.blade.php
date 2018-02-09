@extends('layouts.app')

@section('content')
<div class="container">


<h4 style="padding-left: 50px">KANDIDAT PEMILU GUBERNUR</h4>
  @foreach ($kandidat as $kandidat)
  
   <table border="0" style="padding-left: 50px">
    <tr>
      <td><div class="polaroid"><img height="100px" width="100px" src="{{ asset('image/'.$kandidat->img) }}" ></div></td>
      <td width="450px"> {{ $kandidat->nama }}</td>
     <!-- <td><div class="polaroid"><img height="100px" width="100px" src="{{ asset('image/'.$kandidat->img) }}" ></div></td>
      <td> {{ $kandidat->nama }}</td>-->
    </tr>
   </table>
  @endforeach


    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h3 style="text-transform: capitalize;">Polling {{$type}}</h3>
            <h6 style="text-transform: capitalize;">Masukkan Polling {{$type}}</h6>
                {{Form::open(array('url' => '/vote/save', 'files' => true))}}
                {{ csrf_field() }}
                <p class="name">
              <input type="text" name="tps" class="form-control validate[required,custom[onlyLetter],length[0,100]] feedback-input" placeholder="ID TPS" id="name" required/><br>
                </p>
                
                <p class="name">
                <select name="provinsi" class="form-control validate[required,custom[onlyLetter],length[0,100]] feedback-input provinsi">
                  <option value=""> -Provinsi- </option>
                  @foreach($provinces as $province)
                  <option value="{{ $province->wilayah_id }}">{{ $province->nama }}</option>
                  @endforeach
                </select>
                </p>
              
                <p class="name">
                <select name="kabupaten" class="form-control validate[required,custom[onlyLetter],length[0,100]] feedback-input kabupaten">
                  <option value=""> -Kabupaten/Kota- </option>
                  @foreach($cities as $city)
                  <option value="{{ $city->wilayah_id }}">{{ $city->nama }}</option>
                  @endforeach
                </select>
                </p>
              
                <p class="name">
                <select name="kecamatan" class="form-control validate[required,custom[onlyLetter],length[0,100]] feedback-input kecamatan">
                  <option value=""> -Kecamatan- </option>
                   @foreach($districts as $district)
                  <option value="{{ $district->wilayah_id }}">{{ $district->nama }}</option>
                  @endforeach
                </select>
               </p>
              
                <p class="name">
                <select name="desa" class="form-control validate[required,custom[onlyLetter],length[0,100]] feedback-input desa">
                  <option value=""> -Kelurahan/Desa- </option>
                   @foreach($villages as $village)
                  <option value="{{ $village->wilayah_id }}">{{ $village->nama }}</option>
                  @endforeach
                </select>
                </p>
              
                <br/>
                <p class="name">
                <input type="text" name="kd1" class="form-control validate[required,custom[onlyLetter],length[0,100]] feedback-input" placeholder="Polling kandidat 1" id="name"/>
                </p>
              
                <p class="name">
                <input type="text" name="kd2" class="form-control validate[required,custom[onlyLetter],length[0,100]] feedback-input" placeholder="Polling kandidat 2" id="name"/>
                </p>
              
                <p class="name">
                <input type="text" name="kd3" class="form-control validate[required,custom[onlyLetter],length[0,100]] feedback-input" placeholder="Polling kandidat 3" id="name"/><br>
                </p>

                <p class="name">
                Upload Foto Formulir C1
                <br/>
                <input id="c1photo" type="file" class="form-control" name="c1photo" value="{{ old('c1photo') }}">
                    <br>
                </p>
        <input type="hidden" name="type" value="{{$type}}">


              <div class="submit">
                <input type="submit" name="submit" value="SEND" id="button-blue" class="btn btn-primary form-control" />
                
              </div>
        </form>
        </div>
    </div> 
</div>

@endsection

