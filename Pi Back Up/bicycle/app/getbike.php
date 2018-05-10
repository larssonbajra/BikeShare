<?php
$conn = mysqli_connect("localhost", "root", "bikeserver", "bicycleshare");
$stationid = $_GET['stationno']; 
$query = "SELECT * FROM `bicycle_table` WHERE Station_Id='$stationid' and Bicycle_No NOT IN (SELECT Bicycle_No from password_table);"; 
if($result = mysqli_query($conn, $query)){
    $row_num = mysqli_num_rows($result);
    echo "{";
        echo "\"status\":\"OK\",";
        echo "\"rownum\":\"$row_num\",";
        echo "\"result\":";
            echo "[";
                for($i = 0; $i < $row_num; $i++){
                    $row = mysqli_fetch_array($result);
                    echo "{";
                        echo "\"bicycleno\":\"$row[Bicycle_No]\"";
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
