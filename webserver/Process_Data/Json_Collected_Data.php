<?php
if(isset($_POST['ID']) && isset($_POST['Date_Get'])) {
	$ID=$_POST['ID'];
	$Date_Get=$_POST['Date_Get'];
	<?php
if(isset($_POST['ID']) && isset($_POST['Date_Get'])) {
	$ID=$_POST['ID'];
	$Date_Get=$_POST['Date_Get'];
	//database
    $username="id7269981_lee_solar";
    $password="123456";
    $server = "localhost";
    $dbname = "id7269981_solar";

    $connect=new mysqli($server,$username,$password,$dbname);
    if ($connect->connect_error) {
        die("Error connect database".$connect->connect_error);
        exit();
    }

    $sql = "SELECT * FROM CollectedData WHERE ID='$ID' AND DateGet='$Date_Get'";
    $result = $connect->query($sql);

    if(!$result) {
        die("Error query Users database".$connect->connect_error);
        exit();
    }

    //Tham chieu den tung phan tu trong table
	$data=array();
	$row;
	while ($row = $result -> fetch_array(MYSQLI_ASSOC)) {
		$data[]=$row;
	}
	$connect->close();
	
	echo json_encode($data);
}
?>
}
?>