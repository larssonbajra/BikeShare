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

$password = $_GET['pass'];
$decode = trim(base64_decode($password));
$iv = mcrypt_create_iv(mcrypt_get_iv_size(MCRYPT_RIJNDAEL_256, MCRYPT_MODE_ECB), MCRYPT_RAND);
$decrypted = mcrypt_decrypt(MCRYPT_RIJNDAEL_256, $Key, $decode, MCRYPT_MODE_ECB, $iv);
$string = substr($decrypted,0,4);
$encrypted = encryptIt( $string );
$stat = $_GET['station'];
$doc= $_GET['dock'];
//$doc=substr($docc,0,-1);
//$password = substr($name, -4, 4);



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
		  $docq="select Bike_Avai from dock_table where Station_No='$stat' and Dock_No='$doc';";
 $res = mysqli_query($db, $docq);
   while($row = $res->fetch_assoc()) 
	  {
        $avail=$row["Bike_Avai"];
      }
	  if ($avail=='n')
	  {
	   $q_dockktable="update dock_table set Bike_Avai='y' where Station_No='$stat' and Dock_No='$doc';";
	 $result_dockktable = mysqli_query($db, $q_dockktable);
	
	  $q_biketable="update bicycle_table set In_Use=0, Username=NULL, Station_Id='$stat', Dock_No='$doc' where Bicycle_No='$ubike';";
	 $result_biketable = mysqli_query($db, $q_biketable);
	 $q_park_table="INSERT INTO parking_table (Username, Bicycle_No, Parking_Date, Station_Id) VALUES ('$uname', '$ubike', NOW(), '$stat');";
	 
	 $result_park_table=$db->query($q_park_table);
	 $q_station_table="update station_table set Bicycle_Unit=Bicycle_Unit+1 where Station_Id='$stat';";
	 $station_table=mysqli_query($db, $q_station_table);
	 $q_user_table="update user_table set Active='0' where Username='$uname';";
	 $user_table=mysqli_query($db, $q_user_table);
	 $q_verify_table="update verify_table set Pass_Key='0', Dock_No='$doc' where Station_Id='$stat';";
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
