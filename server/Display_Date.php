<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	if(!empty($_POST['NGAY'])) {
	    $date=$_POST['NGAY'];
	    ?>
	    <!DOCTYPE html>
        <html>
        <head>
	        <title>Chart</title>
	        <script type="text/javascript" src="js/jquery.min.js"></script>
            <script type="text/javascript" src="js/Chart.min.js"></script>
            <style type="text/css">
			#chart-container {
				width: 640px;
				height: auto;
			}
		    </style>
        </head>
        <body>
	        <div id="chart-container">
			    <canvas id="mycanvas"></canvas>
		    </div>
	        <script>
		        var stringDate = "date=" + <?php echo json_encode($date); ?>;
		        var xhttp = new XMLHttpRequest();
		        var ajax_call = function() {
 		        xhttp.onreadystatechange = function() {
    		        if (this.readyState == 4 && this.status == 200) {
    		            var result = JSON.parse(this.responseText);
    		            var NAME = [];
    		            var STT = [];
    		            for (i=0; i<result.length; i++) {
    		                NAME.push(result[i].STT);
    		                STT.push(result[i].STT);
    		            }
    		            var chartdata = {
				            labels: NAME,
				            datasets : [
					            {
						            label: 'STT',
						            data: STT,
                                    borderColor: "#3e95cd",
                                    fill: false,
					            }
				            ]
			            };
			            var ctx = $("#mycanvas");

			            var barGraph = new Chart(ctx, {
				            type: 'line',
				            data: chartdata,
				            options:{animation: false}
			            });
   			        }
  		        };
  		        xhttp.open("POST", "http://ceecuit.tk/History/get_data.php", true);
  		        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  		        xhttp.send(stringDate);
		        }
		        var interval = 2000; // where X is your every X minutes

                setInterval(ajax_call, interval);
	        </script>
        </body>
        </html>
	    <?php
	}
} else {
?>
<!DOCTYPE html>
<html>
<body>

<h2>Use the XMLHttpRequest to get the content of a file.</h2>
<p>The content is written in JSON format, and can easily be converted into a JavaScript object.</p>

<div id="demo">
<script>
var xmlhttp, myObj;
var txt = "<form action=\"Display_Date.php\" method=\"post\">";
xmlhttp = new XMLHttpRequest();
xmlhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        txt+="<p><select name=\"NGAY\">";
        myObj = JSON.parse(this.responseText);
        for (i=0; i<myObj.length; i++) {
            txt += "<option value="+myObj[i]+">"+myObj[i]+"</option>";
        }
        txt+="</select></p>";
        txt+="<p><button type=\"submit\"><br>Gửi</button></p></form>"
        document.getElementById("demo").innerHTML = txt;
    }
};
xmlhttp.open("GET", "Db_Date.php", true);
xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
xmlhttp.send();

</script>
</div>
</body>
</html>
<?php
}
?>