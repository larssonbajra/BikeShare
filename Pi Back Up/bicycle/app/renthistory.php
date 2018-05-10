<?php
$conn = mysqli_connect("localhost", "root", "bikeserver", "bicycleshare");
$user = $_GET['user']; 

$query = "select * from borrow_table where Username='$user'"; 

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
                    echo "\"BicycleNo\":\"$row[Bicycle_No]\", \"BorrowDate\":\"$row[Borrow_Date]\", \"StationId\":\"$row[Station_Id]\"";
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
