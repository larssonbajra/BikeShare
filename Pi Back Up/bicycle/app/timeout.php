<?php
$conn = mysqli_connect("localhost", "root", "bikeserver", "bicycleshare");
$current=time();
$user=$_GET['user'];
while (true)
{
$now=time()-$current;
if ($now==600)
{
	$sql = "SELECT Active,Device_Id FROM `user_table` WHERE Username='$user';"; 
	$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
$dev=$row["Device_Id"];

echo $dev;



        if ($row["Active"]==0)
{
$sqldel = "DELETE FROM password_table WHERE Username='$user'";
if ($conn->query($sqldel) === TRUE) {
    echo "Record deleted successfully";
} else {
    echo "Error deleting record: " . $conn->error;
}
	function sendMessage($data){

		
		$content = array(
			"en" => 'TIMEOUT. REGISTRATION CANCELLED'
			);
		$msg = array
(
	'message' 	=> 'takemessage',
	'title'		=> 'This is a title. title',
	'subtitle'	=> 'This is a subtitle. subtitle',
	'tickerText'	=> 'Ticker text here...Ticker text here...Ticker text here',
	'vibrate'	=> 1,
	'sound'		=> 1,
	'largeIcon'	=> 'large_icon',
	'smallIcon'	=> 'small_icon'
);

		$fields = array(
			'app_id' => "99b5fce3-f7d1-4edc-b0f7-01b92e8f0d85",
					'include_player_ids' => array($data),

			//'included_segments' => $stack,
      'data'			=> $msg,
			'contents' => $content
		);
		
		$fields = json_encode($fields);
    print("\nJSON sent:\n");
    print($fields);
		
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL, "https://onesignal.com/api/v1/notifications");
		curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json; charset=utf-8',
												   'Authorization: Basic YTFiN2RhMjMtNzkwNC00MTA3LWExMWUtYmNiMjNhNjY1OGZm'));
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
		curl_setopt($ch, CURLOPT_HEADER, FALSE);
		curl_setopt($ch, CURLOPT_POST, TRUE);
		curl_setopt($ch, CURLOPT_POSTFIELDS, $fields);
		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);

		$response = curl_exec($ch);
		curl_close($ch);
		
		return $response;
	}
	
	$response = sendMessage($dev);
	$return["allresponses"] = $response;
	$return = json_encode( $return);
	
  print("\n\nJSON received:\n");
	print($return);
  print("\n");
}

    }
} else {
    echo "0 results";
}
$conn->close();
break;
}

}


?>