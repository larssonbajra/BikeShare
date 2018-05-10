<?php 
$db = new mysqli('localhost','root','','bicycleshare');

mysqli_set_charset($db, "utf8");


//$name = $_GET['pass'];
//$password = substr($name, 1);

//$temperature=(int)$temp;


//$humidity=(int)$humi;
$query="select * from password_table;";
 $result = mysqli_query($db, $query);
 

mysqli_close($db);
?>