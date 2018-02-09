<?php
require_once('../database/dbconn.php');
  $nama = '';
  $username ='';
  $email        = '';
  $komentar     = '';
  $flag         = 0;
  if(isset($_POST['btn'])){
      $nama = $_POST['nama'];
      $username = $_POST['username'];  
      $email        = $_POST['email'];
      $komentar     = $_POST['komentar'];
      $id           = $_POST['id'];

      if(!$nama || !$email || !$komentar || !$id){
        $flag = 5;
      }else{
          $insRec       = new MongoDB\Driver\BulkWrite;
          $insRec->update(['_id'=>new MongoDB\BSON\ObjectID($id)],['$set' =>['nama' =>$nama, 'email'=>$email, 'komentar'=>$komentar]], ['multi' => false, 'upsert' => false]);
          $writeConcern = new MongoDB\Driver\WriteConcern(MongoDB\Driver\WriteConcern::MAJORITY, 1000);
          $result       = $manager->executeBulkWrite('test.komen', $insRec, $writeConcern);
          if($result->getModifiedCount()){
            $flag = 3;
          }else{
            $flag = 2;
          } 
      }
  }

  header("Location: index.php?flag=$flag");
  exit;
