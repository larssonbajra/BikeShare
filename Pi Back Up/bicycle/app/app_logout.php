<?php
$conn = mysqli_connect("localhost", "root", "bikeserver", "bicycleshare");
$user = $_GET['user']; 

$query = "update user_table set App_Logged='n' where Username='$user'"; 
if($result = mysqli_query($conn, $query)){
}
else{
    echo "failed to get data from database.";
}
?>
