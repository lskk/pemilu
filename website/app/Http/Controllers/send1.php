<?php
mysql_connect("localhost", "root", "");
mysql_select_db("test");

$tps = $_POST['tps'];
$message = $_POST['message'];

$query = "INSERT INTO message (tps, message) VALUES ('$_POST[tps]','$_POST[message]')";
$hasil = mysql_query($query);

if ($hasil) echo "SMS berhasil dikirim";
else echo "SMS gagal dikirim";

?>