@extends('layouts.app') 
@section('content')

<script>
function myFunction() {
    alert("I am an alert box!");
}
</script>
   

<?php
require_once('../database/dbconn.php');

$flag    = isset($_GET['flag'])?intval($_GET['flag']):0;
$message ='';
if($flag){
$message = $messages[$flag];
}
$filter = ['x' => ['$gt' => 1]];
$filter = [];
$options = [
    'sort' => ['_id' => -1],
];

$query = new MongoDB\Driver\Query($filter, $options);
$cursor = $manager->executeQuery('test.komen', $query);

?>
<!DOCTYPE html>
<style type="text/css">
  #myBtn {
    display: none; /* Hidden by default */
    position: fixed; /* Fixed/sticky position */
    bottom: 20px; /* Place the button at the bottom of the page */
    right: 30px; /* Place the button 30px from the right */
    z-index: 99; /* Make sure it does not overlap */
    border: none; /* Remove borders */
    outline: none; /* Remove outline */
    background-color: black; /* Set a background color */
    color: white; /* Text color */
    cursor: pointer; /* Add a mouse pointer on hover */
    padding: 15px; /* Some padding */
    border-radius: 10px; /* Rounded corners */
}

#myBtn:hover {
    background-color: #555; /* Add a dark-grey background on hover */
}
</style>
<script type="text/javascript">

 

 function showForm() {
            //document.getElementById("dThreshold").display ="block";
            document.getElementById("formSatu").style.display = "block";
        }

  // When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function() {scrollFunction()};

function scrollFunction() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        document.getElementById("myBtn").style.display = "block";
    } else {
        document.getElementById("myBtn").style.display = "none";
    }
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
    document.body.scrollTop = 0; // For Safari
    document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
    showForm();
}

function sortTable() {
  var table, rows, switching, i, x, y, shouldSwitch;
  table = document.getElementById("myTable");
  switching = true;
  /*Make a loop that will continue until no switching has been done:*/
  while (switching) {
    //start by saying: no switching is done:
    switching = false;
    rows = table.getElementsByTagName("tr");
    /*Loop through all table rows (except the first, which contains table headers):*/
    for (i = 1; i < (rows.length - 1); i++) {
      //start by saying there should be no switching:
      shouldSwitch = false;
      /*Get the two elements you want to compare, one from current row and one from the next:*/
      x = rows[i].getElementsByTagName("td")[1];
      y = rows[i + 1].getElementsByTagName("td")[1];
      //check if the two rows should switch place:
      if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
        //if so, mark as a switch and break the loop:
        shouldSwitch= true;
        break;
      }
    }
    if (shouldSwitch) {
      /*If a switch has been marked, make the switch and mark that a switch has been done:*/
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
    }
  }
}


 function myFunction() {
    var x = document.getElementById("beriKomen");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}
 
</script>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>php mongodb tutorial - view insert update delete records</title>
    <link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>
  <button onclick="topFunction()" id="myBtn" title="Go to top">Ke Atas</button>
    <div class="container">
        <div class="row">
            <div class="logo">
                <h3>
                  LIST DATA PEMILIH TETAP
                </h3>
            </div>
        </div>
        <div class="row">
            <div class="span12">
                <div class="mini-layout">
                  <div id="formSatu" style="display: none">
                   <form id="form1" name='form1' action="../record_add.php" method="post"  >
                   <input type='hidden' name='id' id='id' value="" />
                    <table>
                      <tr>
                        <td>Nama Pemilih<br><input type='text' name='username' id='username' placeholder="Identitas Pemilih" disabled /></td>
                        <td>Nama Anda<br><input type='text' name='nama' id='nama' placeholder="Masukkan Nama Anda" pattern="[A-Za-z0-9]{1,20}" required  /></td>
                        <td>Email Anda<br><input type='text' name='email' id='email' placeholder="Masukkan Email Anda" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,13}$" required  /></td>
                        <td>Komentar<br><input type='text' name='komentar' id='komentar' placeholder="Masukkan Komentar Anda" pattern="[A-Za-z0-9]{1,50}" required  /></td>
                        <td><br><input class='btn' type='submit' name='btn' id='btn' value="Beri Komentar" /></td>
                      </tr>Mohon masukkan data berupa huruf a-z atau angka 0-9.
                    </table>
                   </form> 
                 </div>
                    <p> 
                      <?php if($flag == 2 || $flag == 5){ ?>
                        <div class="error"><?php echo $message; ?></div>
                      <?php  } elseif($flag && $flag != 2 ){ ?>
                        <div class="success" "><?php echo $message;  ?></div>
                      <?php  } ?>  
                    </p>
                    <p><button onclick="sortTable()">Sort</button></p>
                    <table class='table table-bordered' id='myTable'>
                      <thead>
                        <tr>
                          <th>Nomor</th>
                          <th>Nama Pemilih</th>
                          <th>Email</th>
                          <th>Aksi</th>
                          <th>Komentar</th>
                        </tr>
                     </thead>
                    <?php 
                    $i =1; 
                    foreach ($cursor as $document) { ?>
                      <tr>
                        <td><?php echo $i; ?></td>
                        <td><?php if ($document->komentar != null ) { 
                              echo '<font color="red">' .$document->username. '</font>'; 
                            }
                            else 
                              echo $document->username;  
                            ?> </td>
                        <td><?php echo $document->email;  ?></td>
                        <td>
                          <a <?php if ($document->komentar != null ) {  ?>
                             style="visibility: hidden;"
                            <?php }
                            else  ?>
                             style="visibility: visible;"
                            id='beriKomen' class='editlink' data-id=<?php echo $document->_id; ?> href='javascript:void(0)' onclick="topFunction();" >Beri Komentar</a>


                        <!--<td><a class='editlink' data-id=<?php echo $document->_id; ?> href='javascript:void(0)' onclick="topFunction()" >Edit</a> |
                          <a onClick ='return confirm("Do you want to remove this record?");' href='record_delete.php?id=<?php echo $document->_id; ?>'>Delete</td>-->
                        <td><?php echo $document->komentar;  ?></td>
                      </tr>
                   <?php $i++;  
                    } 
                  ?>
                    </table>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script>
$(function(){
  $('.editlink').on('click', function(){
    var id = $(this).data('id');
    if(id){
      $.ajax({
          method: "GET",
          url: "record_ajax.php",
          data: { id: id}
        })
        .done(function( result ) {
          result = $.parseJSON(result);          
          $('#username').val(result['username']);
          $('#nama').val(result['nama']);
          $('#email').val(result['email']);
          $('#komentar').val(result['komentar']);
          $('#id').val(result['id']);
          $('#btn').val('Beri Komentar');
          $('#form1').attr('action', 'record_edit.php');
        });
      }
    });
});

</script>
</body>
</html>



@endsection 