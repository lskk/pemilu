<?php
ini_set('display_errors','true');
error_reporting(E_ALL);

$messages = array(
	1=>'Record deleted successfully',
	2=>'Error occured. Please try again.', 
	3=>'Data sukses disimpan.',
    4=>'Data sukses disimpan.', 
    5=>'Semua kolom harus diisi.');
 
$mongoDbname  =  'test';
$mongoTblName =  'komen';
 
$manager = new MongoDB\Driver\Manager("mongodb://pemilu:tJvG9Tsw@167.205.7.226:27017/test");
//$manager     =   new MongoDB\Driver\Manager("mongodb://localhost:27017");