<?php
$conn = mysqli_connect("localhost", "root", "bikeserver", "bicycleshare");
$rate = $_GET['rate']; 
$comment = $_GET['comment']; 
$user = $_GET['user']; 
$dt = new DateTime();
$ratetime=$dt->format('Y-m-d H:i:s');


$query = "insert into rate_table(User,Rate,Comment,RateTime) values ('$user', '$rate', '$comment','$ratetime')";


if($result = mysqli_query($conn, $query)){
}
else{
    echo "failed to get data from database.";
}
?>
