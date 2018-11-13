<?php


$server 	= "localhost:3306";
$username 	= "root";
$password 	= "";
$DB 		= "id7269981_solar";

$ID = $_POST["id"];
$PASS = $_POST["pass"];
$flag = 0;
	$conn = new mysqli($server, $username, $password,$DB);
	if ($conn->connect_error) 
	{
		#die("Connection failed: " . $conn->connect_error);
		echo "-1"; // Server is busy
	} 
	mysqli_query($conn,"SET NAMES 'utf8'");
	
	$query ="SELECT * from users";
	$result = $conn->query($query);
	
		while($row = $result->fetch_assoc()) 
		{
			if($row["ID"] == $ID)
			{
				if($row["Pass"] == $PASS)
				{
					$flag = 1;
					echo $flag."<br>";
					break;
				}
				else 
				{$flag = 2;}
				
			}
			
		}
	
	if($flag == 0)
	{
		
		echo "NOT EXIST ACCOUNT"."<br>";
	}

	else if($flag == 2)
	{
		echo "PASS ERR"."<br>";
	}
	

	//17521115
	//cttkhdFEU221
	
	
	
	
	
	
?>