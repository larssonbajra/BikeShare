<?php
$conn = mysqli_connect("localhost", "root", "bikeserver", "bicycleshare");

$pass = 5555; 

$query="insert into password_table(Bicycle_No,Bicycle_Password,Username) values ('5678',MD5('$pass'),'mrijan');";
		$result = mysqli_query($conn, $query);
		echo "{";
        echo "\"status\":\"OK\",";
        echo "\"rownum\":\"$row_num\",";
        echo "\"result\":";
            echo "[";
			echo "{";
                        echo "\"password\":\"$pass\"";
                    echo "}";
					 echo "]";    
    echo "}";
	

?>
