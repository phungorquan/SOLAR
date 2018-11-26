<?php


$server 	= "localhost:3306";
$username 	= "root";
$password 	= "";
$DB 		= "user";

$ID = $_POST["ID"];

$Code;
$Plant;
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
				$Code = $row["Code"];
				$Plant = $row["PlantNumber"];
				$flag = 1;	
				//echo "EXIST";	// Exist
				break;
			}
		}
		
	if( $flag == 0)
	echo " KHONG TON TAI " ;
	
	
	else if($flag == 1)
	{
		$Plant++;
		$Addplantname = 'plant'."$Plant";
		$DBUSER = $Code;	
		
		// Create connection
		$create = new mysqli($server, $username, $password, $DBUSER);
			// Check connection
			if ($create->connect_error) {
				die("Connection failed: " . $create->connect_error);
			} 

		// sql to create table								
$sql = "CREATE TABLE `$Addplantname` ( 
Field1 float,
Field2 float,
Field3 float,
TimeGet time,
DateGet date)";


			if ($create->query($sql) === TRUE) {
				//echo "Table MyGuests created successfully";
				$update = new mysqli($server, $username, $password, $DB);
			// Check connection
			if ($update->connect_error) {
				die("Connection failed: " . $update->connect_error);
			} 
			
			$sql = "UPDATE user SET PlantNumber = '$Plant' WHERE id = '$ID'";

			if ($update->query($sql) === TRUE) {
				echo "Record updated successfully";
			} else {
				echo "Error updating record: " . $update->error;
			}
			
			} else {
				echo "Error creating table: " . $create->error;
			}

	}
	
	
	// if($flag == 0)
	// {
		
		
		
	// $findcode ="SELECT Max(Code) from user";
	// $result = $conn->query($findcode);
	// while($row = $result->fetch_assoc()) 
	// {
		// $Newcode = $row["Max(Code)"];
	// }
	
	// $Newcode +=1;
	// //echo 
	
		
		// echo "NEWCODE = " . "$Newcode";
		
		// $insert = "INSERT INTO user(Code,ID,PASS) VALUES ('$Newcode','$ID','$PASS')";
		// if ($conn->query($insert) === TRUE)
		// {
			// echo "CAN ACCESS" ;//"Can acces"."<br>";
			
			// // Create connection
				// $conn = new mysqli($server, $username, $password);
			// // Check connection
				// if ($conn->connect_error) {
					// die("Connection failed: " . $conn->connect_error);
				// } 
					
				// $Changepath = str_replace('"', "'", $Newcode);
			// // Create new database for each neu user
			// $sql = "CREATE DATABASE `$Newcode` ";
				// if ($conn->query($sql) === TRUE) {
					// echo "Database created successfully";
				// } else {
					// echo "Error creating database: " . $conn->error;
						// }
			
			
		// }
		// else echo "ERROR"; // server is busy
		
		
			
			
	//}
	
	//17521115
	//cttkhdFEU221
	
	


$conn->close();
?>
