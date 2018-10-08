<?php
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

$sql = "SELECT ID FROM Users";
$result = $connect->query($sql);

if(!$result) {
    die("Error query Users database".$connect->connect_error);
    exit();
}

$row;
$List_User;
while ($row = $result -> fetch_array(MYSQLI_ASSOC)) {
    $List_User[]=$row['ID'];
}

foreach ($List_User as $ID) {
    $sql = "UPDATE CurrentData
            SET StatusConnect=0
            WHERE ID = '$ID'";
    $result = $connect->query($sql);
    if(!$result) {
        die("ERROR Write Disconnect".$connect->connect_error);
        exit();
    }
    else {
        echo "OK";
    }
}

$connect->close();
?>