<?php
function decryptIt( $q ) {
    $cryptKey  = 'qJB0rGtIn5UB1xG03efyCp';
    $qDecoded      = rtrim( mcrypt_decrypt( MCRYPT_RIJNDAEL_256, md5( $cryptKey ), base64_decode( $q ), MCRYPT_MODE_CBC, md5( md5( $cryptKey ) ) ), "\0");
    return( $qDecoded );
}
$conn = mysqli_connect("localhost", "root", "bikeserver", "bicycleshare");
$user = $_GET['user']; 


$query = "select * from password_table where Username='$user'"; 

if($result = mysqli_query($conn, $query)){
    $row_num = mysqli_num_rows($result);
    echo "{";
        echo "\"status\":\"OK\",";
        echo "\"rownum\":\"$row_num\",";
        echo "\"result\":";
            echo "[";
                for($i = 0; $i < $row_num; $i++){
                    $row = mysqli_fetch_array($result);
					$decrypted = decryptIt( $row[Bicycle_Password] );
					
			
			


                    echo "{";
                        echo "\"Bicycle_Password\":\"$decrypted\"";
                    echo "}";
                    if($i<$row_num-1){
                        echo ",";
                    }
                }
            echo "]";    
    echo "}";
}
else{
    echo "failed to get data from database.";
}
?>
