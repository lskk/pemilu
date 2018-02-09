<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Pemilu</title>


    <!-- Fonts -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css" integrity="sha384-XdYbMnZ/QjLh6iI4ogqCTaIjrFk87ip+ekIjefZch0Y+PvJ8CDYtEs1ipDmPorQ+" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato:100,300,400,700">
    <link rel="stylesheet" type="text/css" href={!! asset('css/stylehome.css') !!}>
    <link rel="stylesheet" type="text/css" href={!! asset('css/style1.css') !!}>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>  
    <!-- Styles -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    {{-- <link href="{{ elixir('css/app.css') }}" rel="stylesheet"> --}}

    <style>
        body {
            font-family: 'Lato';
        }

        .fa-btn {
            margin-right: 6px;
        }
        .timeline-username {
            text-align: center;
        }
        .timeline-likes a {
            color: gray;
        }
        .timeline-likes a.liked {
            color: skyblue;
        }
        .timeline-likes a.unliked {
            color: maroon;
        }
 
        /* Dropdown Content (Hidden by Default) */
        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            min-width: 200px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            z-index: 1;
       }

        /* Links inside the dropdown */
        .dropdown-content a {
            color: black;
            padding: 28px 26px;
            text-decoration: none;
            display: block;
        }

        /* Change color of dropdown links on hover */
        .dropdown-content a:hover {
            color: white;
            background-color: #29b7ad}

        /* Show the dropdown menu on hover */
        .dropdown:hover .dropdown-content {
            
            width:50px;
            display: block;
        }

        /* Change the background color of the dropdown button when the dropdown content is shown */
        .dropdown:hover .dropbtn {
            background-color: #3e8e41;

        }
     

    </style>
</head>
<body id="app-layout">
    <div id="fb-root"></div>
    <script>(function(d, s, id) {
      var js, fjs = d.getElementsByTagName(s)[0];
      if (d.getElementById(id)) return;
      js = d.createElement(s); js.id = id;
      js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.10&appId=1949997921808321";
      fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));</script>

<div id="header">
    <nav>    
    <ul>
    <a href="{{ url('/') }}"><img src="../image/logo.png"></a>
        <li><a href="{{ url('/home') }}">HOME</a></li>
        <!--<li><a href="{{ url('/maps') }}">MAPS</a>-->
        <li><a href="{{ url('http://localhost/rabbitmqHost/resources/views/welcome.blade.php')  }}">MAPS</a>
        <div class="dropdown-content">
       <!--     <a href="{{ url('http://localhost/rabbit-mq/resources/views/welcome.blade.php') }}">WALIKOTA</a> -->
            <a href="{{ url('http://localhost/rabbitmqHost/resources/views/welcome.blade.php') }}">GUBERNUR</a>
        </div>
        </li>
        <li><a href="{{ url('/report') }}">REPORT</a></li>
        <li><a href="{{ url('/rules') }}">PEMILIH</a></li>

   
 


                    <!-- Authentication Links -->
                    @if (Auth::guest())
                      <!--  <li><a href="{{ url('/login') }}">Login</a></li>-->
                        <li><a href="{{ url('/register') }}">REGISTER</a></li>
                    @else
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                                {{ Auth::user()->name }} <span class="caret"></span>
                            </a>

                            <ul class="dropdown-menu" role="menu">
                                <li><a href="{{ url('/logout') }}"><i class="fa fa-btn fa-sign-out"></i>Logout</a></li>
                            </ul>
                        </li>
                    @endif
                </ul>



    </nav>


 
    @yield('content')

    <!-- JavaScripts -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.3/jquery.min.js" integrity="sha384-I6F5OKECLVtK/BL+8iSLDEHowSAfUo76ZL9+kGAgTRdiByINKJaqTPH/QVNS1VDb" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
    {{-- <script src="{{ elixir('js/app.js') }}"></script> --}}






<footer>
<div id="footer">  
            <br/><br/><br/>
            Institut Teknologi Bandung<br/>
            Jalan Ganesha no 10<br/>
            Bandung<br/><br/>
            <a href="www.facebook.com"><img src="{{url('image/sosmedFb.png')}}" width="30px" height="30px"></a>
            <a href="www.twitter.com"><img src="{{url('image/sosmedTwit.png')}}" width="30px" height="30px"></a>
            <a href="www.google.com"><img src="{{url('image/sosmedGugel.png')}}" width="30px" height="30px"></a>
</div>​​​​​​​​​
</footer>


    <script type="text/javascript">
        $(document).ready(function(){
    $('.provinsi').change(function(){
      var id = $(this).val();
      var tingkat = '1';
      var to = '.kabupaten';

      if(id)
        getChild(id,tingkat,to);
      else
        $(to).append('<option value="">-Kelurahan/Desa-</option>');
    });
    $('.kabupaten').change(function(){
      var id = $(this).val();
      var tingkat = '2';
      var to = '.kecamatan';

      if(id)
        getChild(id,tingkat,to);
      else
        $(to).append('<option value="">-Kelurahan/Desa-</option>');
    });
    $('.kecamatan').change(function(){
      var id = $(this).val();
      var tingkat = '3';
      var to = '.desa';

      if(id)
        getChild(id,tingkat,to);
      else
        $(to).append('<option value="">-Kelurahan/Desa-</option>');
    });
  
    function getChild(id,tingkat,to){
      $.ajax({
        url : '{{ url("getChildWilayah") }}/'+id,
        type : 'GET',
        success : function(data){
          var result = getOption(JSON.parse(data),tingkat);
          $(to).html('');
          $(to).append(result);
          $(to).change();
        },
        error : function(a,b,c){}
      })
    }

    function getOption(data,tingkat){
      var txt = '<option value="">'+(tingkat==1?'-Kabupaten/Kota-':(tingkat==2?'-Kecamatan-':'-Kelurahan/Desa-'))+'</option>';
      
      $.each(data,function(i,v){
        txt += '<option value="'+v.wilayah_id+'">'+v.nama+'</option>';
      });

      return txt;
    }
  });
    </script>
</body>
</html>
