<?php


    $Key = "S@ftware12345678";

function encryptIt( $q ) {

    $cryptKey  = 'qJB0rGtIn5UB1xG03efyCp';

    $qEncoded      = base64_encode( mcrypt_encrypt( MCRYPT_RIJNDAEL_256, md5( $cryptKey ), $q, MCRYPT_MODE_CBC, md5( md5( $cryptKey ) ) ) );

    return( $qEncoded );

}




session_start();
$db = new mysqli('localhost','root','bikeserver','bicycleshare');

mysqli_set_charset($db, "utf8");

$name = $_GET['pass'];
$stat = $_GET['station'];
//$password = substr($name, -4, 4);
$password = $_GET['pass'];
echo $password;
$decode = trim(base64_decode($password));


$iv = mcrypt_create_iv(mcrypt_get_iv_size(MCRYPT_RIJNDAEL_256, MCRYPT_MODE_ECB), MCRYPT_RAND);

$decrypted = mcrypt_decrypt(MCRYPT_RIJNDAEL_256, $Key, $decode, MCRYPT_MODE_ECB, $iv);

$string = substr($decrypted,0,4);
$encrypted = encryptIt( $string );
echo $stat;

$query="select Username, Bicycle_No from password_table where Bicycle_Password='$encrypted';";
 $result = mysqli_query($db, $query);

 $num_rows = mysqli_num_rows($result);
 if($num_rows==1)
 {
	 
	 
	 $bike="select Username,Bicycle_No from password_table where Bicycle_Password='$encrypted';";
	 $resbike = mysqli_query($db, $bike);
	  while($row = $resbike->fetch_assoc()) 
	  {
        $uname=$row["Username"];
		$ubike=(int)$row["Bicycle_No"];
		
    }
	 
	 $station="select Station_Id, Dock_No from bicycle_table where Bicycle_No=$ubike;";
	 $resstation = mysqli_query($db, $station);
	 while($row = $resstation->fetch_assoc()) 
	  {
        $stationno=$row["Station_Id"];
		$dockno=$row["Dock_No"];
	  }
	  $docq="select Bike_Avai from dock_table where Station_No='$stationno' and Dock_No='$dockno';";
 $res = mysqli_query($db, $docq);
   while($row = $res->fetch_assoc()) 
	  {
        $avail=$row["Bike_Avai"];
      }
	  if ($avail=='y')
	  {
		 $q_dockktable="update dock_table set Bike_Avai='n' where Station_No='$stationno' and Dock_No='$dockno';";
	 $result_dockktable = mysqli_query($db, $q_dockktable);
	  $q_biketable="update bicycle_table set In_Use=1, Username='$uname', Station_Id=NULL, Dock_No=NULL where Bicycle_No='$ubike';";
	 $result_biketable = mysqli_query($db, $q_biketable);
	 $q_borrow_table="INSERT INTO borrow_table (Username, Bicycle_No, Borrow_Date, Station_Id) VALUES ('$uname', '$ubike', NOW(), '$stationno');";
	 
	 $result_borrow_table=$db->query($q_borrow_table);
	 $q_station_table="update station_table set Bicycle_Unit=Bicycle_Unit-1 where Station_Id='$stationno';";
	 $station_table=mysqli_query($db, $q_station_table);
	 $q_user_table="update user_table set Active='1' where Username='$uname';";
	 $user_table=mysqli_query($db, $q_user_table);
	 $q_verify_table="update verify_table set Pass_Key='1', Dock_No='$dockno' where Station_Id='$stationno';";
	 $verify_table_table=mysqli_query($db, $q_verify_table);
	  }
	  else
	  {
     $q_verify_table="update verify_table set Dock_No=0 where Station_Id='$stat';";
	 $verify_table_table=mysqli_query($db, $q_verify_table);
	  }
	  
 }
 else
 {
	  $q_verify_table="update verify_table set Dock_No=0 where Station_Id='$stat';";
	 $verify_table_table=mysqli_query($db, $q_verify_table);
 }



mysqli_close($db);
?>
