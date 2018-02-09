 <?php
    //include "dbconfig.php"; 
 
    echo "Kandidat dengan nilai tertinggi adalah ";

      $sum1 = mysql_query("SELECT SUM(kan1) FROM polling");
      $test1 = mysql_fetch_array($sum1); 
      $sum2 = mysql_query("SELECT SUM(kan2) FROM polling");
      $test2 = mysql_fetch_array($sum2); 
      $sum3 = mysql_query("SELECT SUM(kan3) FROM polling");
      $test3 = mysql_fetch_array($sum3); 

      $jumlah=$test1[0]+$test2[0]+$test3[0]; 

      if ($test1[0]<$test3[0] && $test2[0]<$test3[0])
      {
      	$max=$test3[0];
      	$kandidatmax=3;
      }
      elseif ($test1[0]<$test2[0] && $test3[0]<$test2[0])
      {
      	$max=$test2[0];
      	$kandidatmax=2;
      }
      else 
      {
      	$max=$test1[0];
      	$kandidatmax=1;
      }

     // $persentase=($max/$jumlah)*100;
      //$pembulatan=number_format($persentase,2);
		$persentase1=($test1[0]/$jumlah)*100;
		$persentase2=($test2[0]/$jumlah)*100;
		$persentase3=($test3[0]/$jumlah)*100;
		$pembulatan1=number_format($persentase1,2);
		$pembulatan2=number_format($persentase2,2);
		$pembulatan3=number_format($persentase3,2);
    echo "kandidat nomor $kandidatmax dengan perolehan suara sebesar $max dari total $jumlah suara. <br><br><br><h2>Kandidat 1 : $pembulatan1 persen. 
	<br>Kandidat 2 : $pembulatan2 persen. <br>Kandidat 3 : $pembulatan3 persen.</h2>";
 
 ?>   