@extends('layouts.app')
@section('content')

<!--<body style='background-image:url({{asset("image/bg.jpg")}}); background-size:1400px 800px; '>-->


<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-1">
            <div class="panel panel-default">
                <div class="panel-heading">Login</div>
                <div class="panel-body">
                    <form class="form-horizontal" role="form" method="POST" action="{{ url('/login') }}">
                        {{ csrf_field() }}

                        <div class="form-group{{ $errors->has('email') ? ' has-error' : '' }}">
                            <label for="email" class="col-md-4 control-label">Email</label>

                            <div class="col-md-6">
                                <input id="email" type="email" class="form-control" name="email" value="{{ old('email') }}">

                                @if ($errors->has('email'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('email') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>

                        <div class="form-group{{ $errors->has('password') ? ' has-error' : '' }}">
                            <label for="password" class="col-md-4 control-label">Password</label>

                            <div class="col-md-6">
                                <input id="password" type="password" class="form-control" name="password">

                                @if ($errors->has('password'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('password') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-4">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa fa-btn fa-sign-in"></i> Login
                                </button>

                            </div>
                        </div>
                    </form>
                </div>
            </div>

        </div>
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">Peringkat 10 Tertinggi</div>
                <div class="panel-body">
                    <div>
                      <?php $order=1; ?>
                      @foreach($usersPoin as $userPoin)
                        <div class="row">
                          <div class="col-md-2">
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

