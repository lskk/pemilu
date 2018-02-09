@extends('layouts.app')

@section('content')
<html>
<body>




<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">Register</div>
                <div class="panel-body">
                  {{Form::open(array('url' => '/register', 'files' => true))}}
                        {{ csrf_field() }}


            
                
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
          





                        <div class="form-group{{ $errors->has('name') ? ' has-error' : '' }}">
                            <label for="name" class="col-md-4 control-label">Name</label>

                            <div class="col-md-6">
                                <input id="name" type="text" class="form-control" name="name" value="{{ old('name') }}">

                                @if ($errors->has('name'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('name') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>

                        <div class="form-group{{ $errors->has('email') ? ' has-error' : '' }}">
                            <label for="email" class="col-md-4 control-label">E-Mail Address</label>

                            <div class="col-md-6">
                                <input id="email" type="email" class="form-control" name="email" value="{{ old('email') }}">

                                @if ($errors->has('email'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('email') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>


                        <div class="form-group{{ $errors->has('email') ? ' has-error' : '' }}">
                            <label for="phone" class="col-md-4 control-label">Telepon</label>

                            <div class="col-md-6">
                                <input id="phone" type="number" class="form-control" name="phone" value="{{ old('phone') }}">

                                @if ($errors->has('phone'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('phone') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>


                        <div class="form-group{{ $errors->has('email') ? ' has-error' : '' }}">
                            <label for="address" class="col-md-4 control-label">Alamat</label>

                            <div class="col-md-6">
                                <textarea id="text" type="address" class="form-control" name="address">{{ old('address') }}</textarea>
                                @if ($errors->has('address'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('address') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>


                        <div class="form-group{{ $errors->has('email') ? ' has-error' : '' }}">
                            <label for="photo" class="col-md-4 control-label">Foto</label>
                            <div class="col-md-6">
                                <input id="photo" type="file" class="form-control" name="photo" value="{{ old('photo') }}">
                                @if ($errors->has('photo'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('photo') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>

                        <hr/>

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

                        <div class="form-group{{ $errors->has('password_confirmation') ? ' has-error' : '' }}">
                            <label for="password-confirm" class="col-md-4 control-label">Confirm Password</label>

                            <div class="col-md-6">
                                <input id="password-confirm" type="password" class="form-control" name="password_confirmation">

                                @if ($errors->has('password_confirmation'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('password_confirmation') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-4">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa fa-btn fa-user"></i> Register
                                </button>
                            </div>
                        </div>
                    </form>
               </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
@endsection
