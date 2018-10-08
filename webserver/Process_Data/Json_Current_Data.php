<?php
if(isset($_POST['ID'])) {
	<?php
if(isset($_POST['ID'])) {
    $ID=$_POST['ID'];
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

    $sql = "SELECT * FROM CurrentData WHERE ID='$ID'";
    $result = $connect->query($sql);

    if(!$result) {
        die("Error query Users database".$connect->connect_error);
        exit();
    }

    //Tham chieu den tung phan tu trong table
    $row;
    $data;
    while ($row = $result -> fetch_array(MYSQLI_ASSOC)){
        $data=$row;
    }
    $connect->close();
    
    echo json_encode($data);
}
?>
}
?>