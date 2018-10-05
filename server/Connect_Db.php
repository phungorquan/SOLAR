<?php
$username="id6531159_manager";
$password="123456";
$server = "localhost";
$dbname = "id6531159_ceecdoor_list_id";

$connect=new mysqli($server,$username,$password,$dbname);
if ($connect->connect_error) {
	die("ERROR".$connect->connect_error);
	exit();
}
?>