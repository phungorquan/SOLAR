const char MAIN_page[] PROGMEM = R"=====(
<!DOCTYPE html>
<html>

<head>
<title>Solar System</title>
<meta charshet="utf-8" />
<style>
body {font-family: Arial, Helvetica, sans-serif;}
/* Data Table Styling */
#dataTable {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

#dataTable td, #dataTable th {
	border: 1px solid #ddd;
	padding: 8px;
}

#dataTable tr:nth-child(even){background-color: #f2f2f2;}

#dataTable tr:hover {background-color: #ddd;}

#dataTable th {
	padding-top: 12px;
	padding-bottom: 12px;
	text-align: left;
	background-color: #4CAF50;
	color: white;
}

.warning {
	color: red;
}

.great {
	color: green;
}
</style>
</head>

<body>

<p style="text-align:center;color:MediumSeaGreen;font-size:200%;">
  <strong>Solar System Infomation: CEEC</span></strong>
</p>
<div>
	<table id="dataTable">
	  <tr>
	  	<th>Last time update</th>
	  	<th>PV_Vol (V)</th>
	  	<th>PV_Amp (A)</th>
	  	<th>Bus (V)</th>
	  	<th>AC_Vol (V)</th>
	  	<th>AC_Hz (Hz)</th>
	  	<th>Temp (C)</th>
	  	<th>Pac (W)</th>
	  	<th>EToday (KWh)</th>
	  	<th>EAll (KWh)</th>
	  	<th>Status</th>
	  </tr>
	</table>
</div>

<script>
setInterval(function() {getData();}, 5000); //5000mSeconds update rate
/*-----------------------get curent info------------------*/
function getData() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
            var InfoCurrent_Html = "";
            var result_current = JSON.parse(this.responseText);
            var timeGet = new Date().toLocaleTimeString();

            var table = document.getElementById("dataTable");
            table.rows[1].cells[0].innerHTML=timeGet;
            table.rows[1].cells[1].innerHTML=result_current.PV_Vol;
            table.rows[1].cells[2].innerHTML=result_current.PV_Amp;
            table.rows[1].cells[3].innerHTML=result_current.Bus;
            table.rows[1].cells[4].innerHTML=result_current.AC_Vol;
            table.rows[1].cells[5].innerHTML=result_current.AC_Hz;
            table.rows[1].cells[6].innerHTML=result_current.Tem;
            table.rows[1].cells[7].innerHTML=result_current.Pac;
            table.rows[1].cells[8].innerHTML=result_current.EToday;
            table.rows[1].cells[9].innerHTML=result_current.EAll;
            if(result_current.StatusConnect==1) {
                table.rows[1].cells[10].innerHTML='<p class="great">On-line</p';
            } else {
                table.rows[1].cells[10].innerHTML='<p class="warning">Off-line</p';
            }
        }
    };
    xhttp.open("GET", "current", true);
    xhttp.send();
};
var table = document.getElementById("dataTable");
var row = table.insertRow(1);	//Add after headings
row.insertCell(0).innerHTML = '...';
row.insertCell(1).innerHTML = '...';
row.insertCell(2).innerHTML = '...';
row.insertCell(3).innerHTML = '...';
row.insertCell(4).innerHTML = '...';
row.insertCell(5).innerHTML = '...';
row.insertCell(6).innerHTML = '...';
row.insertCell(7).innerHTML = '...';
row.insertCell(8).innerHTML = '...';
row.insertCell(9).innerHTML = '...';
row.insertCell(10).innerHTML = '...';
</script>
</body>
</html>

)=====";
