<?php
  include "dbconfig.php";

            $query = mysql_query("select * from polling");
          while ($data = mysql_fetch_array($query)) 
            { 
              $id = $id['id_desa'];
              $kan1 = $data['kan1']; 
              $kan2 = $data['kan1']; 
              $kan3 = $data['kan3']; 
              echo ("addMarker($lat, $lon, 'Tps : $nama');\n");  
              //echo ("addMarker($lat, $lon, 'Tps : $nama<br>Kandidat 1 : $kandidat1%<br>Kandidat 2 : $kandidat2%<br>Kandidat 3 : $kandidat3%');\n");                        
            } 
?>