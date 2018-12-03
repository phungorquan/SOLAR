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
		echo "-1"; // Server is busy
	} 
	mysqli_query($conn,"SET NAMES 'utf8'");
	
	$query ="SELECT * from user";
	$result = $conn->query($query);
	
		while($row = $result->fetch_assoc()) 
		{
			if($row["ID"] == $ID)
			{
				$flag = 1;	
				echo "EXIST";	// Exist
				break;
			}
		}
		
	
	
	if($flag == 0)
	{
	
	$findcode ="SELECT Max(Code) from user";
	$result = $conn->query($findcode);
	while($row = $result->fetch_assoc()) 
	{
		$Newcode = $row["Max(Code)"];
	}
	
	$Newcode +=1;
		
		$insert = "INSERT INTO user(Code,ID,PASS) VALUES ('$Newcode','$ID','$PASS')";
		if ($conn->query($insert) === TRUE)
		{
			echo "CAN ACCESS" ;//"Can acces"."<br>";
			
			// Create connection
				$conn = new mysqli($server, $username, $password);
			// Check connection
				if ($conn->connect_error) {
					die("Connection failed: " . $conn->connect_error);
				} 
					
			// Create new database for each new user
			$sql = "CREATE DATABASE `$Newcode` ";
				if ($conn->query($sql) === TRUE) {
					echo "Database created successfully";
				} else {
					echo "Error creating database: " . $conn->error;
						}
			
			
		}
		else echo "ERROR"; // server is busy
		
		
			
			
	}
	
	//17521115
	//cttkhdFEU221
	
	


$conn->close();
?>
