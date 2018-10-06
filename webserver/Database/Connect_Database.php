<?php
$username="id6561449_ceec";
$password="123456";
$server = "localhost";
$dbname = "id6561449_solar";

$connect=new mysqli($server,$username,$password,$dbname);
if ($connect->connect_error) {
	die("ERROR".$connect->connect_error);
	exit();
}
?>