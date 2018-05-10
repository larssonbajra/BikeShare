<?php
$conn = mysqli_connect("localhost", "root", "bikeserver", "bicycleshare");
$user = $_GET['user']; 
$pass = $_GET['pass']; 
$query = "select * from user_table where Username='$user' and Password=MD5('$pass') and App_Logged='n'"; 

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
                        echo "\"username\":\"$row[Username]\", \"password\":\"$row[Password]\"";
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
