<?php
session_start();
$conn = mysqli_connect("localhost", "root", "bikeserver", "bicycleshare");
mysqli_set_charset($conn, "utf8");
$station=$_GET['stat'];
$dock=0;
$verify="select Dock_No from verify_table where Station_Id='$station';";
	 $q_verify = mysqli_query($conn, $verify);
	  while($row = $q_verify->fetch_assoc()) 
	  {
        $dock=$row["Dock_No"];
		
	  }
	  echo "+".$dock."|";
session_unset();
session_destroy();
mysqli_close($conn);
?>
