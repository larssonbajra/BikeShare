<?php
$conn = mysqli_connect("localhost", "root", "bikeserver", "bicycleshare");
$username = $_GET['username']; 
$pass = $_GET['password']; 
$firstname = $_GET['firstname']; 
$familyname = $_GET['familyname']; 
$email = $_GET['email']; 

$query = "insert into user_table(App_Logged, First_Name, Last_Name, Email, Username, Password, Use_Limit, Active) values ('n', '$firstname', '$familyname', '$email', '$username', MD5('$pass'), now(), '0')";
echo "$query";

if($result = mysqli_query($conn, $query)){
}
else{
    echo "failed to get data from database.";
}
?>
