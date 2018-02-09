@extends('layouts.app')
@section('content')

<div class="container">
    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <div class="panel panel-default">
                <div class="panel-heading">Welcome</div>

                <div class="panel-body">
                    
 @foreach ($users as $users)
  
   <table border="0" style="padding-left: 50px">
    <tr>
      <td></td>
      <td width="450px"> {{ $users->name }}</td>
     <!-- <td><div class="polaroid"><img height="100px" width="100px" src="{{ asset('image/'.$kandidat->img) }}" ></div></td>
      <td> {{ $kandidat->nama }}</td>-->
    </tr>
   </table>
  @endforeach




                </div>
            </div>
        </div>
    </div>
</div>
@endsection