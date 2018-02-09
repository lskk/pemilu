<?php
require_once('../database/dbconn.php');
$id    = $_GET['id'];
$result = array();
if($id){
  $filter = ['_id' => new MongoDB\BSON\ObjectID($id)];
  $options = [
   'projection' => ['_id' => 0],
];
  $query = new MongoDB\Driver\Query($filter,$options);
  $cursor = $manager->executeQuery('test.komen', $query);
  foreach($cursor as $row){
    $result ['nama'] = $row->nama;
    $result ['username'] = $row->username;
    $result ['email']        = $row->email;
    $result ['komentar']     = $row->komentar;
    $result ['id']           = $id;
  }
  echo json_encode($result);
}
