<?php
$conn = mysqli_connect("localhost", "root", "bikeserver", "bicycleshare");
$user = $_GET['username']; 
$device = $_GET['device']; 
$query = "update user_table set Device_Id='$device' where Username='$user'"; 

if($result = mysqli_query($conn, $query)){
   
                 echo "OK";
}
else{
    echo "failed to get data from database.";
}
?>
