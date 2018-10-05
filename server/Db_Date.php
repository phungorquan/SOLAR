<?php
	//setting header to json
	header('Content-Type: application/json');
	include 'Connect_Db.php';
	$sql = "SELECT * FROM history";
	$result = $connect->query($sql);

	if(!$result) {
		die("ERROR".$connect->connect_error);
		exit();
	}

	//Tham chieu den tung phan tu trong table
	$row;
	$previous="";
	$data = array();
	while ($row = $result -> fetch_array(MYSQLI_ASSOC)) {
	    if($row['NGAY']!=$previous) {
		    $data[]=$row['NGAY'];
		    $previous=$row['NGAY'];
	    }
	}
	$result->close();
	$connect->close();
	print json_encode($data);
?>