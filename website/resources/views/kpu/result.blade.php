<html>  
<head>
  <!---<link rel="stylesheet" type="text/css" href={!! asset('css/style1.css') !!}>-->
  <link rel="stylesheet" type="text/css" href={!! asset('css/stylehome.css') !!}>
  <link rel="stylesheet" type="text/css" href={!! asset('css/style1.css') !!}>
  <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>  
<!--<body style='background-image:url({{asset("image/bg.jpg")}}); background-size:1350px 1000px;   height:150%;'>-->
 
</head>


<style>

/* Dropdown Content (Hidden by Default) */
.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f9f9f9;
    min-width: 160px;
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
.dropdown-content a:hover {background-color: #f1f1f1}

/* Show the dropdown menu on hover */
.dropdown:hover .dropdown-content {
    display: block;
}

/* Change the background color of the dropdown button when the dropdown content is shown */
.dropdown:hover .dropbtn {
    background-color: #3e8e41;

}
</style>

<body>
<div id="container">
<div id="header">
  <nav>    
    <ul>
    <a href="{{ url('formsms') }}"><img src="image/logo.png"></a>
    <li><a href="#">HOME</a></li>
    <li><a href="#">PEMILU</a>
    <div class="dropdown-content">
      <a href="{{ url('formWalkot')}}">WALIKOTA</a>
      <a href="{{ url('formsms') }}">GUBERNUR</a>
    </div>
    </li> 
    <li><a href="{{ url('../resources/views/welcome.blade.php') }}" id="mapspage">MAPS</a>
    <div class="dropdown-content">
      <a href="{{ url('../resources/views/welcome_walkot.blade.php') }}">WALIKOTA</a>
      <a href="{{ url('../resources/views/welcome.blade.php') }}">GUBERNUR</a>
    </div>
    </li>
    <li><a href="{{ url('../resources/views/test.html') }}" id="testpage">COUNT</a></li>
  </ul> 
  </nav>
</div>
 

<div id="body">
 <div id="form-main" >
  <div id="">
    <form class="form" id="form1" method="post" action="http://localhost:8000/kpu/fetch">
          <input type="hidden" name="_token" value="{{ csrf_token() }}">
            
            <p class="name">
            <select name="provinsi" class="validate[required,custom[onlyLetter],length[0,100]] feedback-input provinsi">
              <option value=""> -Provinsi- </option>
              @foreach($provinces as $province)
              <option value="{{ $province->wilayah_id }}">{{ $province->nama }}</option>
              @endforeach
            </select>
            </p>
          
            <p class="name">
            <select name="kabupaten" class="validate[required,custom[onlyLetter],length[0,100]] feedback-input kabupaten">
              <option value=""> -Kabupaten/Kota- </option>
            </select>
            </p>
          
            <p class="name">
            <select name="kecamatan" class="validate[required,custom[onlyLetter],length[0,100]] feedback-input kecamatan">
              <option value=""> -Kecamatan- </option>
            </select>
           </p>
          
            <p class="name">
            <select name="desa" class="validate[required,custom[onlyLetter],length[0,100]] feedback-input desa">
              <option value=""> -Kelurahan/Desa- </option>
            </select>
            </p>
          
     

          <div class="submit">
            <input type="submit" name="submit" value="SEND" id="button-blue"/>
            <div class="ease"></div>
          </div>
    </form>
  </div>
  <div>
    <h2>Data Dari API KPU</h2>
    <h3>Polling Provinsi - <?php echo $pollingProvinsiName." - ".$provinsi; ?></h3>
    <table border=1>
      <tr>
        <th>
          Wilayah ID
        </th>
        <th>
          Wilayah
        </th>
        <th>
          Parent ID
        </th>
        <th>
          Daerah ID
        </th>
        <th>
          Tingkat
        </th>
        <th>
          Jumlah TPS
        </th>

      </tr>
    <?php
    foreach ($pollingProvinsi as $key => $value) {
      ?>
      <tr>
      <td><?php echo $value->wilayah_id ?></td>
      <td><?php echo $value->wilayah ?></td>
      <td><?php echo $value->parent ?></td>
      <td><?php echo $value->daerah ?></td>
      <td><?php echo $value->tingkat ?></td>
      <td><?php echo $value->jumlah_tps ?></td>
      </tr>
      <?php
    }
    ?>
    </table>


  </div>
  </div>


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
  

</div>




<footer>
<div id="footer">  
      <br/><br/><br/>
        Institut Teknologi Bandung<br/>
        Jalan Ganesha no 10<br/>
        Bandung<br/><br/>
        <a href="www.facebook.com"><img src="image/sosmedFb.png" width="30px" height="30px"></a>
        <a href="www.twitter.com"><img src="image/sosmedTwit.png" width="30px" height="30px"></a>
        <a href="www.google.com"><img src="image/sosmedGugel.png" width="30px" height="30px"></a>
</div>​​​​​​​​​
</footer>
</body>
</html> 