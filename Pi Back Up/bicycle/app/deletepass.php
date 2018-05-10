<?php
$conn = mysqli_connect("localhost", "root", "bikeserver", "bicycleshare");
$user = $_GET['user']; 

$query = "delete from password_table where Username='$user';"; 
$result = mysqli_query($conn, $query);

?>
