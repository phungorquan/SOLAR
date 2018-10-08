<?php
/*$Current_Data =
{
    "Current":
    {
        "Voltage":  ,
        "Intensity":  ,
        "Status":
    },
    "User":
    {
        "ID":  ,
        "Pass":  ,
    }
}
*/
/*$Collected_Data =
{
    "Field":
    {
        "Field1":   ,
        "Field2":   ,
        "Field3":
    },
    "User":
    {
        "ID":  ,
        "Pass":  ,
    }
}
*/
if (isset($_POST['Collected'])) {
    $Collected_Data = json_decode($_POST['Collected']);
    $ID=$Collected_Data->{'User'}->{'ID'};
    $Pass=$Collected_Data->{'User'}->{'Pass'};
    $Field1=$Collected_Data->{'Field'}->{'Field1'};
    $Field2=$Collected_Data->{'Field'}->{'Field2'};
    $Field3=$Collected_Data->{'Field'}->{'Field3'};

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

    $sql = "SELECT 1 FROM Users WHERE ID='$ID' AND Pass='$Pass'";
    $result = $connect->query($sql);

    if(!$result) {
        die("Error query Users database".$connect->connect_error);
        exit();
    }
    //if right password and user
    if(mysqli_num_rows($result) > 0) {
        date_default_timezone_set("Asia/Ho_Chi_Minh");
        $DateGet=date("Y-m-d");
        $TimeGet=date("h:i");
        $sql = "INSERT INTO CollectedData (ID, Field1, Field2, Field3, TimeGet, DateGet)
                VALUES ('$ID','$Field1', '$Field2', '$Field3', '$TimeGet', '$DateGet')";
        $result = $connect->query($sql);
        if(!$result) {
            die("ERROR Insert Collected Data".$connect->connect_error);
            exit();
        }
        else {
            echo "OK";
        }
    }
    $connect->close();
}
if (isset($_POST['Current'])) {
    $Current_Data = json_decode($_POST['Current']);

    $ID=$Current_Data->{'User'}->{'ID'};
    $Pass=$Current_Data->{'User'}->{'Pass'};

    $Voltage=$Current_Data->{'Current'}->{'Voltage'};
    $Intensity=$Current_Data->{'Current'}->{'Intensity'};
    $Wat=$Current_Data->{'Current'}->{'Wat'};
    $Status_Connect=$Current_Data->{'Current'}->{'Status_Connect'};

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
    /*----------------check user and password------------------*/
    $sql = "SELECT 1 FROM Users WHERE ID='$ID' AND Pass='$Pass'";
    $result = $connect->query($sql);

    if(!$result) {
        die("Error query Users database".$connect->connect_error);
        exit();
    }
    /*----------------if user enter right password => write data to database------------------*/
    if(mysqli_num_rows($result) > 0) {
        $sql = "UPDATE CurrentData
                SET Voltage = '$Voltage', Intensity= '$Intensity', Wat='$Wat',StatusConnect='$Status_Connect'
                WHERE ID = '$ID'";
        $result = $connect->query($sql);
        if(!$result) {
            die("ERROR Update Current Data".$connect->connect_error);
            exit();
        }
        else {
            echo "OK";
        }
    }
    $connect->close();
}
?>