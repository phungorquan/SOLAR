$(document).ready(function(){
    ajax_call = function() {
	$.ajax({
		url: "http://ceecuit.tk/History/Display_Date.php",
		method: "GET",
		success: function(data) {
			console.log(data);
			var time = [];
			var w = [];

			for(var i in data) {
				time.push(data[i].NAME);
				w.push(data[i].STT);
			}

			var chartdata = {
				labels: time,
				datasets : [
					{
						label: 'Watt',
						data: w,
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
		},
		error: function(data) {
			console.log(data);
		}
	});
    };
var interval = 1000;
setInterval(ajax_call, interval);
});