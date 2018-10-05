<?php
    if(!empty($_POST['date'])) {
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
        $data = array();
        while ($row = $result -> fetch_array(MYSQLI_ASSOC)) {
            if($row['NGAY']==$_POST['date']) {
                $data[]=$row;
            }
        }
        $result->close();
        $connect->close();
        print json_encode($data);
    }

?>