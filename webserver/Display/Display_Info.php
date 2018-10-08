<?php
	session_start();
	if(!isset($_SESSION["authenticated"]) ||  $_SESSION["authenticated"]!= 'true') { ?>
		<!DOCTYPE html>
		<html>
		<head>
			<title>Solar System</title>
		</head>
		<body>
		<p><strong>You must login first!</strong></p>
		<a href="http://lee-ceec.000webhostapp.com/solar/Process_Data/login.php">Login page</a> 
		</body>
		</html>
<?php
	} else { ?>
		<!DOCTYPE html>
		<html>
		<head>
		<title>Solar System</title>
		<meta charshet="utf-8" />
		<script type="text/javascript" src="../js/jquery.min.js"></script>
        <script type="text/javascript" src="../js/Chart.min.js"></script>
		<style type="text/css"></style>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<style>
		body {font-family: Arial, Helvetica, sans-serif;}

		/* Full-width input fields */
		.Info {
		    width: 100%;
		    padding: 12px 20px;
		    margin: 8px 0;
		    display: inline-block;
		    border: 3px solid #ccc;
		    box-sizing: border-box;
		}

		.Sel_box {
		    width: 50%;
		    padding: 12px 20px;
		    margin: 8px 0;
		    display: inline-block;
		    border: 3px solid #ccc;
		    box-sizing: border-box;
		}

		.Sel_mem {
		    background-color: #4CAF50;
		    color: white;
		    padding: 14px 20px;
		    margin: 8px 0;
		    border: none;
		    cursor: pointer;
		    width: 96%;
		}

		.Sel_mem:hover,
		.Sel_mem:focus {
		    color: red;
		    cursor: pointer;
		}

		/* Set a style for all buttons */
		button {
		    background-color: #4CAF50;
		    color: white;
		    padding: 14px 20px;
		    margin: 8px 0;
		    border: none;
		    cursor: pointer;
		    width: 100%;
		}

		button:hover {
		    opacity: 0.8;
		}

		/* Extra styles for the cancel button */
		.cancelbtn {
		    width: auto;
		    padding: 10px 18px;
		    background-color: #f44336;
		}

		/* Center the image and position the close button */
		.imgcontainer {
		    text-align: center;
		    margin: 24px 0 12px 0;
		    position: relative;
		}

		img.avatar {
		    width: 40%;
		    border-radius: 50%;
		}

		.container {
		    padding: 16px;
		}

		span.psw {
		    float: right;
		    padding-top: 16px;
		}

		/* The Modal (background) */
		.modal {
		    display: none; /* Hidden by default */
		    position: fixed; /* Stay in place */
		    z-index: 1; /* Sit on top */
		    left: 0;
		    top: 0;
		    width: 100%; /* Full width */
		    height: 100%; /* Full height */
		    overflow: auto; /* Enable scroll if needed */
		    background-color: rgb(0,0,0); /* Fallback color */
		    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
		    padding-top: 60px;
		}

		/* Modal Content/Box */
		.modal-content {
		    background-color: #fefefe;
		    margin: 5% auto 15% auto; /* 5% from the top, 15% from the bottom and centered */
		    border: 1px solid #888;
		    width: 80%; /* Could be more or less, depending on screen size */
		}

		/* The Close Button (x) */
		.close {
		    position: absolute;
		    right: 25px;
		    top: 0;
		    color: #000;
		    font-size: 35px;
		    font-weight: bold;
		}

		.close:hover,
		.close:focus {
		    color: red;
		    cursor: pointer;
		}

		/* Add Zoom Animation */
		.animate {
		    -webkit-animation: animatezoom 0.6s;
		    animation: animatezoom 0.6s
		}

		@-webkit-keyframes animatezoom {
		    from {-webkit-transform: scale(0)} 
		    to {-webkit-transform: scale(1)}
		}
		    
		@keyframes animatezoom {
		    from {transform: scale(0)} 
		    to {transform: scale(1)}
		}

		/* Change styles for span and cancel button on extra small screens */
		@media screen and (max-width: 300px) {
		    span.psw {
		       display: block;
		       float: none;
		    }
		    .cancelbtn {
		       width: 100%;
		    }
		}
		</style>
		</head>
		<body>

		<h2>Select Date Below</h2>
		<div id="SelectDate"></div>
		<p></p><a href="http://lee-ceec.000webhostapp.com/solar/Process_Data/logout.php">Log out</a></p>
		<div id="InfoBlock" class="modal">
		    <div class="modal-content animate">
		        <div>
		            <p style="text-align:center;color:MediumSeaGreen;font-size:200%;">
		                <strong>Solar System Infomation: <span id="User_Name"></span></strong>
		            </p>
		        </div>
		    
		        
		        <div class="imgcontainer">
		          <span onclick="Close_Click()" class="close" title="Close Modal">&times;</span>
		          <div id=ImgSolar></div>
		        </div>
		        <canvas id=chart width="auto" height="auto"></canvas>
		        <div id=Info_Current class="container"></div>
		    </div>
		</div>

		<script>
		var xmlhttp, DateObj;
		var UserID="ID="+<?php echo json_encode($_SESSION['User']) ?>;
		var txt="<select onclick=\"List(this);\" size=";
		xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
        		DateObj = JSON.parse(this.responseText);
        		txt+=DateObj.length+">";
        		for (i=0; i<DateObj.length; i++) {
        		    txt += "<option style=\"text-align:left;font-size:160%;\">"+DateObj[i].DateGet;
        		}
        		txt+="</select>";
        		document.getElementById("SelectDate").innerHTML = txt;
    	    }
    	};
    		xmlhttp.open("POST", "http://lee-ceec.000webhostapp.com/solar/Process_Data/Json_Date.php", true);
    		xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    		xmlhttp.send(UserID); //send to get all date have data that belong to User ID

		var intervalId;
		var ajax_call;
		function List(x) {
		    /*-------------------------------selected date--------------------*/
		    var whichSelected = x.selectedIndex;
		    var Sel_Date = x.options[whichSelected].text;
		    var UserID="ID=" + <?php echo json_encode($_SESSION['User']); ?>;
		    var ID=<?php echo json_encode($_SESSION['User']); ?>;
		    /*-------------------------------Current------------------------*/
		    document.getElementById('User_Name').innerHTML=ID;
		    var xhttp_Current = new XMLHttpRequest();
		    var PostGetCurrent = UserID;
		    /*-------------------------------Collected------------------------*/
		    var xhttp_Collected = new XMLHttpRequest();
		    var PostGetCollected = UserID + "&Date_Get=" + Sel_Date;
		    /*-----------------------get curent and collected info------------------*/
		    ajax_call = function() {
		    	/*---------------------Get current data-------------------*/
		        xhttp_Current.onreadystatechange = function() {
		            if (this.readyState == 4 && this.status == 200) {
		                var InfoCurrent_Html = "<div class=\"container\">";
		                var result_current = JSON.parse(this.responseText);
		                console.log(result_current);
		                InfoCurrent_Html+="<div class=\"Info\">";
		                InfoCurrent_Html+="<p>Current Data:</p>"
		                InfoCurrent_Html+="<p>Voltage(V): " + result_current.Voltage + "</p>";
		                InfoCurrent_Html+="<p>Intensity(A): " + result_current.Intensity + "</p>";
		                InfoCurrent_Html+="<p>Wat(W): " + result_current.Wat + "</p>";
		                if(result_current.StatusConnect==1) {
		                    InfoCurrent_Html+="<p>Status: Online</p>";
		                } else {
		                    InfoCurrent_Html+="<p>Status: Offline</p>";
		                }
		                InfoCurrent_Html+="</div>";
		                InfoCurrent_Html+="</div>"
		                document.getElementById('Info_Current').innerHTML=InfoCurrent_Html;
		                document.getElementById('Info_Current').style.display='block';
		            }
		        };
		        xhttp_Current.open("POST", "http://lee-ceec.000webhostapp.com/solar/Process_Data/Json_Current_Data.php", true);
		        xhttp_Current.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		        xhttp_Current.send(PostGetCurrent);

		        /*-------------------------Get collected data-----------------*/
		        xhttp_Collected.onreadystatechange = function() {
		            if (this.readyState == 4 && this.status == 200) {
		            	var result_collected = JSON.parse(this.responseText);
		            	console.log(result_collected);
        		        var TIME = [];
        		        var FIELD1 = [];
        		        var FIELD2 = [];
        		        var FIELD3 = [];
        		        for (i=0; i<result_collected.length; i++) {
        		            TIME.push(result_collected[i].TimeGet);
        		            FIELD1.push(result_collected[i].Field1);
        		            FIELD2.push(result_collected[i].Field2);
        		            FIELD3.push(result_collected[i].Field3);
        		        }
        		        var chartdata = {
    				        labels: TIME,
    				        datasets : [
    					        {
    						        label: 'field 1',
    						        data: FIELD1,
                                    borderColor: "#3e95cd",
                                    fill: false,
    					        }, {
    					            label: 'field 2',
    						        data: FIELD2,
                                    borderColor: "#8e5ea2",
                                    fill: false,
    					        }, {
    					            label: 'field 3',
    						        data: FIELD3,
                                    borderColor: "#3cba9f",
                                    fill: false,
    					        }
    				        ]
    			        };
    			        new Chart(document.getElementById("chart"),{
    				        type: 'line',
    				        data: chartdata,
    				        options:{animation: false}
    			        });
    			        document.getElementById('chart').style.display='block';
		            }
		        };
		        xhttp_Collected.open("POST", "http://lee-ceec.000webhostapp.com/solar/Process_Data/Json_Collected_Data.php", true);
		        xhttp_Collected.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		        xhttp_Collected.send(PostGetCollected);
		    }
		    start_interval(); //set timer
		    /*-------------------------------place avatar----------------------*/
		    var Src_Img;
		    var Src_Img_Html="<img src=\"http://lee-ceec.000webhostapp.com/img/solar.jpg\" alt=\"Avatar\" class=\"avatar\">";
		    document.getElementById('ImgSolar').innerHTML=Src_Img_Html;
		    /*------------------------------display this block------------------*/
		    document.getElementById('InfoBlock').style.display='block';
		};
		function start_interval() {
			if (intervalId) {
		        clearInterval(intervalId);
		    }
		    intervalId = setInterval(ajax_call, 1000);
		}
		// Get the modal
		var modal = document.getElementById('InfoBlock');

		// When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
		    if (event.target == modal) {
		        Close_Click();
		    }
		};
		    
		function Close_Click() {
		    document.getElementById('InfoBlock').style.display='none';
		    clearInterval(intervalId);
		    document.getElementById('Info_Current').innerHTML="";
		    document.getElementById('Info_Current').style.display='none';
		    document.getElementById('chart').innerHTML="";
		    document.getElementById('chart').style.display='none';
		};
		</script>

		</body>
		</html>
<?php
	}
?>