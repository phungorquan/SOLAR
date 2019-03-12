var socket = io.connect('http://localhost:3000');
//current list nodes
socket.on("server-send-list-nodes", function(data) {
	if (data) {
		var htmlString="<select id='dropDownNodes'>";
		data.forEach(function(i) {
			htmlString=htmlString.concat("<option value='" + i.NodeID + "'>"+i.NodeName+"</option>");
		});
		htmlString=htmlString.concat("</select>");
		$("#selectNodeForm").html(htmlString);

		$("#loginForm").hide(500);
		$("#nodesForm").show(500);
	} else {
		alert("there's no node found! please refresh again");
	}
});

//current list nodes
socket.on("server-send-current-data", function(data) {
	if (data) {
		$("#TimeGet").html("Last time updated: "+data.TimeGet);
		$("#PV_Vol").html("PV voltage: "+data.PV_Vol);
		$("#PV_Amp").html("PV ampe: "+data.PV_Amp);
		$("#Bus").html("Bus voltage: "+data.Bus);
		$("#AC_Vol").html("AC voltage: "+data.AC_Vol);
		$("#AC_Hz").html("AC Frequency: "+data.AC_Hz);
		$("#Tem").html("Temperature: "+data.Tem);
		$("#Pac").html("Pac: "+data.Pac);
		$("#EToday").html("Today energy: "+data.EToday);
		$("#EAll").html("Total energy: "+data.EAll);
		if (data.StatusConnect) {
			$("#StatusConnect").html("Status: Connected");
		} else {
			$("#StatusConnect").html("Status: Disconnected");
		}
		
		$("#nodesForm").hide(500);
		$("#dataForm").show(500);
	}
});

socket.on("server-send-collected-today", function(data) {
	console.log(data);
});

$(document).ready(function() {
	$("#loginForm").show();
	$("#dataForm").hide();
	$("#nodesForm").hide();

	$("#btnLogin").click(function() {
		var username = $("#txtUsername").val();
		var password = $("#txtPassword").val();
		socket.emit("log", {username: username, password: password, typeClient:"web-client"});
	});

	$("#btnSelectNode").click(function() {
		socket.emit("client-send-selected-node", $( "#dropDownNodes" ).val());
	});
});