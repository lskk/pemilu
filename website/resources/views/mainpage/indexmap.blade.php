<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href={!! asset('css/style.css') !!}>
	<link rel="stylesheet" type="text/css" href="http://cdn.leafletjs.com/leaflet/v0.7.7/leaflet.css" />
    <link rel="stylesheet" type="text/css" href="http://cdnjs.cloudflare.com/ajax/libs/leaflet.markercluster/0.4.0/MarkerCluster.css" />
    <link rel="stylesheet" type="text/css" href="http://cdnjs.cloudflare.com/ajax/libs/leaflet.markercluster/0.4.0/MarkerCluster.Default.css" />
	
	<script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js'></script>
    <script type='text/javascript' src='http://cdn.leafletjs.com/leaflet/v0.7.7/leaflet.js'></script>
    <script type='text/javascript' src='http://cdnjs.cloudflare.com/ajax/libs/leaflet.markercluster/0.4.0/leaflet.markercluster.js'></script>
  
</head>
<body>

	<form id="tokenOnly">
		<input type="hidden" name="_token" value="{{ csrf_token() }}">
	</form>

	<div id="ket">
		<h1>Hasil TPS</h1>
		<div id="hasilTps">
			
		</div>
	</div>
  
	<div id="map"></div>
	<script type='text/javascript' src={!! asset('js/markers.js') !!}></script>	
	<script type='text/javascript' src={!! asset('js/leaf-demo.js') !!}></script>
	
	<script>
		$(document).ready(function(){
			var ids = [];
			for ( var i = 0; i < markers.length; i++ )
			{
				var url = markers[i].url;
				var id = url.substring(url.lastIndexOf('/') + 1);
				ids.push(id);
			}
			
			$input = {
				'nama' : 'testing',
				'_token' : $("#tokenOnly input[name='_token']").val(),
				'ids' : ids
			};
			
			$('#hasilTps').html('');
			$.post('{{URL::route("postIndex")}}', $input, function(response){
					$('#hasilTps').html(response);
			});
		});
	</script>
	 
</body>
</html>
