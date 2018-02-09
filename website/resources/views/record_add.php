<?php
 require_once('../database/dbconn.php');
  $nama = '';
  $username ='';
  $email = '';
  $komentar = '';
  $flag = 0;
  if(isset($_POST['btn'])){
      $nama = $_POST['nama'];
      $username = $_POST['username'];      
      $email = $_POST['email'];
      $komentar = $_POST['komentar'];

      if(!$nama || !$email || !$komentar){
        $flag = 5;
      }else{
          $insRec       = new MongoDB\Driver\BulkWrite;
          $insRec->insert(['nama' =>$nama, 'email'=>$email, 'komentar'=>$komentar]);
          $writeConcern = new MongoDB\Driver\WriteConcern(MongoDB\Driver\WriteConcern::MAJORITY, 1000);
          $result       = $manager->executeBulkWrite('test.komen', $insRec, $writeConcern);

          if($result->getInsertedCount()){
            $flag = 3;
          }else{
            $flag = 2;
          }
      }
  }
  header("Location: index.php?flag=$flag");
  exit;
