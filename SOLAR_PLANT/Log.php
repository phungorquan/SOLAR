<?php


$server 	= "localhost:3306";
$username 	= "root";
$password 	= "";
$DB 		= "user";

$ID = $_POST["ID"];
$PASS = $_POST["PASS"];
$flag = 0;
	$conn = new mysqli($server, $username, $password,$DB);
	if ($conn->connect_error) 
	{
		#die("Connection failed: " . $conn->connect_error);
		echo "-1"; # Server is busy
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
					$flag = 2;
					echo $flag;
					break;
				}
				else 
				{$flag = 1;}
				
			}
			
		}
	
	if($flag == 0)
	{
		echo "0";//"NOT EXIST ACCOUNT";
	}

	else if($flag == 1)
	{
		echo "1";//"PASS ERR"."<br>";
	}
	

	//17521115
	//cttkhdFEU221
	
	
	
	
	
	
?>