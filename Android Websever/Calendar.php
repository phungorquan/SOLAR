<?php
$server 	= "localhost:3306";
$username 	= "root";
$password 	= "";
$DB 		= "id7269981_solar";

$ID = $_POST["Getid"];
$Date = $_POST["DATE"];
		
		class SV 
	{
		function SV($F1,$F2,$F3)
		{
			$this->F1 = $F1;
			$this->F2 = $F2;
			$this->F3 = $F3;
		}
		
	};
	$mangSV = array();
	

	
	$connect = mysqli_connect($server ,$username, $password ,$DB );
	mysqli_query($connect,"SET NAMES 'utf8'");
	
	//#$query = "SELECT Max(Field1),Max(Field2),Max(Field3) from collecteddata where (SELECT Max(DateGet) from collecteddata where ID = '$ID')";
	$query = "SELECT Max(Field1),Max(Field2),Max(Field3) from collecteddata where (SELECT (ID = '$ID') AND DateGet = '$Date')";
	$data = mysqli_query($connect,$query);
	
	while($row = mysqli_fetch_assoc($data))
	{
		//echo $row['Name']."<br>";
		//echo $row['MSSV']."<br>";
		
		array_push($mangSV,new SV($row['Max(Field1)'],$row['Max(Field2)'],$row['Max(Field3)']));		
	}

	
	echo json_encode($mangSV);


?>