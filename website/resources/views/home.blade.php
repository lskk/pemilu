@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <div class="row">
              <div class="col-md-4">
                <h3>
                Data Diri
                </h3><br>
                <div>
                  <img src="{{url('fotoprofil/'.Auth::user()->photo)}}" width="100px">
                </div>
                <div>
                  <br>
                  <table>
                    <tr>
                      <th> Nama </th>
                      <td> &emsp;&emsp;: </td>
                      <td> {{Auth::user()->name}} </td>
                    </tr>
                    <tr>
                      <th> Email </th>
                      <td> &emsp;&emsp;: </td>
                      <td> {{Auth::user()->email}} </td>
                    </tr>
                    <tr>
                      <th> Telepon </th>
                      <td> &emsp;&emsp;: </td>
                      <td> {{Auth::user()->phone}} </td>
                    </tr>
                    <tr>
                      <th> Alamat </th>
                      <td> &emsp;&emsp;: </td>
                      <td> {{Auth::user()->address}} </td>
                    </tr>
                
                  </table>
                  <br>
                </div>
                <div>
                  <p><h4>Masukkan Hasil Polling</h4></p>
                  <!--<p>
                    <a href="{{url('vote/walkot')}}" class="btn-primari">Walikota</a>
                  </p>
                  <p>-->
                    <a href="{{url('vote/gubernur')}}" class="btn-primari">Gubernur</a>
                  </p>


                </div>
              </div>

              <div class="col-md-6">
                <h3>
                  Peringkat 10 Tertinggi
                </h3>
                <hr/>
                <div>
                  <?php $order=1; ?>
                  @foreach($usersPoin as $userPoin)
                    <div class="row">
                      <div class="col-md-3">
                        <img src="{{url('fotoprofil/'.$userPoin->photo)}}" width="100%">
                      </div>
                      <div class="col-md-6">
                        <p>Rangking #{{$order}}</p>
                        <p>{{$userPoin->name}}</p>
                        <p>Poin : <b>{{$userPoin->total_poin}} Poin</b></p>
                      </div>
                    </div>
                    <hr/>
                    <?php $order++; ?>
                  @endforeach
                </div>
              </div>

            </div>
        </div>
    </div>
</div>
@endsection
