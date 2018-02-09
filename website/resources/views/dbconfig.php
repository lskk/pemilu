<?php
$host = '167.205.7.226';
$user = 'root';
$pass = '';
$db = 'pemilu';
$koneksi    = mysql_connect($host,$user,$pass);
     
    if(!$koneksi){
        die("Cannot connect to database.");
    }
     
    mysql_select_db($db);
 
?>