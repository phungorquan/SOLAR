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
?>