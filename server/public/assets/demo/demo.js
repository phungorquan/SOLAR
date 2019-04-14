
var dataChartCurrent={labels:[], dataContent:[]},
    dataChartEveryDay={labels:[], dataContent:[]},
    dataChartEveryMonth={labels:[], dataContent:[]},
    dataChartEveryYear={labels:[], dataContent:[]};

var chartCurrent, chartEveryDay, chartEveryMonth, chartEveryYear;

userChart = {
  resetChartCurrent: function () {
    while (chartCurrent.data.labels.length) {
      chartCurrent.data.labels.pop();
      chartCurrent.data.datasets[0].data.pop();
    }
    chartCurrent.update();
  },
  resetChartEveryDay: function() {
    while (chartEveryDay.data.labels.length) {
      chartEveryDay.data.labels.pop();
      chartEveryDay.data.datasets[0].data.pop();
    }
    chartEveryDay.update();
  },
  resetChartEveryMonth: function() {
    while (chartEveryMonth.data.labels.length) {
      chartEveryMonth.data.labels.pop();
      chartEveryMonth.data.datasets[0].data.pop();
    }
    chartEveryMonth.update();
  },
  resetChartEveryYear: function() {
    while (chartEveryYear.data.labels.length) {
      chartEveryYear.data.labels.pop();
      chartEveryYear.data.datasets[0].data.pop();
    }
    chartEveryYear.update();
  },
  resetAllChart: function() {
    while (chartCurrent.data.labels.length) {
      chartCurrent.data.labels.pop();
      chartCurrent.data.datasets[0].data.pop();
    }
    chartCurrent.update();

    while (chartEveryDay.data.labels.length) {
      chartEveryDay.data.labels.pop();
      chartEveryDay.data.datasets[0].data.pop();
    }
    chartEveryDay.update();

    while (chartEveryMonth.data.labels.length) {
      chartEveryMonth.data.labels.pop();
      chartEveryMonth.data.datasets[0].data.pop();
    }
    chartEveryMonth.update();

    while (chartEveryYear.data.labels.length) {
      chartEveryYear.data.labels.pop();
      chartEveryYear.data.datasets[0].data.pop();
    }
    chartEveryYear.update();
  },
  updateChartCurrent: function () {
    for (let i=0; i<dataChartCurrent.labels.length; i++) {
      chartCurrent.data.labels.push(dataChartCurrent.labels[i]);
      chartCurrent.data.datasets[0].data.push(dataChartCurrent.dataContent[i]);
    }
    chartCurrent.update();
  },
  updateChartEveryDay: function () {
    while (chartEveryDay.data.labels.length) {
      chartEveryDay.data.labels.pop();
      chartEveryDay.data.datasets[0].data.pop();
    }
    for (let i=0; i<dataChartEveryDay.labels.length; i++) {
      chartEveryDay.data.labels.push(dataChartEveryDay.labels[i]);
      chartEveryDay.data.datasets[0].data.push(dataChartEveryDay.dataContent[i]);
    }
    chartEveryDay.update();
  },
  updateChartEveryMonth: function () {
    while (chartEveryMonth.data.labels.length) {
      chartEveryMonth.data.labels.pop();
      chartEveryMonth.data.datasets[0].data.pop();
    }
    for (let i=0; i<dataChartEveryMonth.labels.length; i++) {
      chartEveryMonth.data.labels.push(dataChartEveryMonth.labels[i]);
      chartEveryMonth.data.datasets[0].data.push(dataChartEveryMonth.dataContent[i]);
    }
    chartEveryMonth.update();
  },
  updateChartEveryYear: function () {
    while (chartEveryYear.data.labels.length) {
      chartEveryYear.data.labels.pop();
      chartEveryYear.data.datasets[0].data.pop();
    }
    for (let i=0; i<dataChartEveryYear.labels.length; i++) {
      chartEveryYear.data.labels.push(dataChartEveryYear.labels[i]);
      chartEveryYear.data.datasets[0].data.push(dataChartEveryYear.dataContent[i]);
    }
    chartEveryYear.update();
  },
  updateAllChart: function () {
    while (chartCurrent.data.labels.length) {
      chartCurrent.data.labels.pop();
      chartCurrent.data.datasets[0].data.pop();
    }
    for (let i=0; i<dataChartCurrent.labels.length; i++) {
      chartCurrent.data.labels.push(dataChartCurrent.labels[i]);
      chartCurrent.data.datasets[0].data.push(dataChartCurrent.dataContent[i]);
    }
    chartCurrent.update();

    while (chartEveryDay.data.labels.length) {
      chartEveryDay.data.labels.pop();
      chartEveryDay.data.datasets[0].data.pop();
    }
    for (let i=0; i<dataChartEveryDay.labels.length; i++) {
      chartEveryDay.data.labels.push(dataChartEveryDay.labels[i]);
      chartEveryDay.data.datasets[0].data.push(dataChartEveryDay.dataContent[i]);
    }
    chartEveryDay.update();

    while (chartEveryMonth.data.labels.length) {
      chartEveryMonth.data.labels.pop();
      chartEveryMonth.data.datasets[0].data.pop();
    }
    for (let i=0; i<dataChartEveryMonth.labels.length; i++) {
      chartEveryMonth.data.labels.push(dataChartEveryMonth.labels[i]);
      chartEveryMonth.data.datasets[0].data.push(dataChartEveryMonth.dataContent[i]);
    }
    chartEveryMonth.update();

    while (chartEveryYear.data.labels.length) {
      chartEveryYear.data.labels.pop();
      chartEveryYear.data.datasets[0].data.pop();
    }
    for (let i=0; i<dataChartEveryYear.labels.length; i++) {
      chartEveryYear.data.labels.push(dataChartEveryYear.labels[i]);
      chartEveryYear.data.datasets[0].data.push(dataChartEveryYear.dataContent[i]);
    }
    chartEveryYear.update();
  },
  initDashboardPageCharts: function (dataChartCurrent, dataChartEveryDay, dataChartEveryMonth, dataChartEveryYear) {

    chartColor = "#FFFFFF";

    // General configuration for the charts with Line gradientStroke
    gradientChartOptionsConfiguration = {
      maintainAspectRatio: false,
      legend: {
        display: false
      },
      tooltips: {
        bodySpacing: 4,
        mode: "nearest",
        intersect: 0,
        position: "nearest",
        xPadding: 10,
        yPadding: 10,
        caretPadding: 10
      },
      responsive: 1,
      scales: {
        yAxes: [{
          display: 0,
          gridLines: 0,
          ticks: {
            display: false
          },
          gridLines: {
            zeroLineColor: "transparent",
            drawTicks: false,
            display: false,
            drawBorder: false
          }
        }],
        xAxes: [{
          display: 0,
          gridLines: 0,
          ticks: {
            display: false
          },
          gridLines: {
            zeroLineColor: "transparent",
            drawTicks: false,
            display: false,
            drawBorder: false
          }
        }]
      },
      layout: {
        padding: {
          left: 0,
          right: 0,
          top: 15,
          bottom: 15
        }
      }
    };

    gradientChartOptionsConfigurationWithNumbersAndGrid = {
      maintainAspectRatio: false,
      legend: {
        display: false
      },
      tooltips: {
        bodySpacing: 4,
        mode: "nearest",
        intersect: 0,
        position: "nearest",
        xPadding: 10,
        yPadding: 10,
        caretPadding: 10
      },
      responsive: true,
      scales: {
        yAxes: [{
          gridLines: 0,
          gridLines: {
            zeroLineColor: "transparent",
            drawBorder: false
          }
        }],
        xAxes: [{
          display: 0,
          gridLines: 0,
          ticks: {
            display: false
          },
          gridLines: {
            zeroLineColor: "transparent",
            drawTicks: false,
            display: false,
            drawBorder: false
          }
        }]
      },
      layout: {
        padding: {
          left: 0,
          right: 0,
          top: 15,
          bottom: 15
        }
      }
    };

    var ctx = document.getElementById('bigDashboardChart').getContext("2d");

    var gradientStroke = ctx.createLinearGradient(500, 0, 100, 0);
    gradientStroke.addColorStop(0, '#80b6f4');
    gradientStroke.addColorStop(1, chartColor);

    var gradientFill = ctx.createLinearGradient(0, 200, 0, 50);
    gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");
    gradientFill.addColorStop(1, "rgba(255, 255, 255, 0.24)");

    chartCurrent = new Chart(ctx, {
      type: 'line',
      data: {
        labels: dataChartCurrent.labels,
        datasets: [{
          label: "(W)EveryMinute",
          borderColor: chartColor,
          pointBorderColor: chartColor,
          pointBackgroundColor: "#1e3d60",
          pointHoverBackgroundColor: "#1e3d60",
          pointHoverBorderColor: chartColor,
          pointBorderWidth: 1,
          pointHoverRadius: 7,
          pointHoverBorderWidth: 2,
          pointRadius: 5,
          fill: true,
          backgroundColor: gradientFill,
          borderWidth: 2,
          data: dataChartCurrent.dataContent
        }]
      },
      options: {
        layout: {
          padding: {
            left: 20,
            right: 20,
            top: 0,
            bottom: 0
          }
        },
        maintainAspectRatio: false,
        tooltips: {
          backgroundColor: '#fff',
          titleFontColor: '#333',
          bodyFontColor: '#666',
          bodySpacing: 4,
          xPadding: 12,
          mode: "nearest",
          intersect: 0,
          position: "nearest"
        },
        legend: {
          position: "bottom",
          fillStyle: "#FFF",
          display: false
        },
        scales: {
          yAxes: [{
            ticks: {
              fontColor: "rgba(255,255,255,0.4)",
              fontStyle: "bold",
              beginAtZero: true,
              maxTicksLimit: 5,
              padding: 10
            },
            gridLines: {
              drawTicks: true,
              drawBorder: false,
              display: true,
              color: "rgba(255,255,255,0.1)",
              zeroLineColor: "transparent"
            }

          }],
          xAxes: [{
            gridLines: {
              zeroLineColor: "transparent",
              display: false,

            },
            ticks: {
              padding: 10,
              fontColor: "rgba(255,255,255,0.4)",
              fontStyle: "bold"
            }
          }]
        }
      }
    });

    var cardStatsMiniLineColor = "#fff",
      cardStatsMiniDotColor = "#fff";

    ctx = document.getElementById('lineChartExample').getContext("2d");

    gradientStroke = ctx.createLinearGradient(500, 0, 100, 0);
    gradientStroke.addColorStop(0, '#80b6f4');
    gradientStroke.addColorStop(1, chartColor);

    gradientFill = ctx.createLinearGradient(0, 170, 0, 50);
    gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");
    gradientFill.addColorStop(1, "rgba(249, 99, 59, 0.40)");

    chartEveryDay = new Chart(ctx, {
      type: 'line',
      responsive: true,
      data: {
        labels: dataChartEveryDay.labels,
        datasets: [{
          label: "(W)EveryDay",
          borderColor: "#f96332",
          pointBorderColor: "#FFF",
          pointBackgroundColor: "#f96332",
          pointBorderWidth: 2,
          pointHoverRadius: 4,
          pointHoverBorderWidth: 1,
          pointRadius: 4,
          fill: true,
          backgroundColor: gradientFill,
          borderWidth: 2,
          data: dataChartEveryDay.dataContent
        }]
      },
      options: gradientChartOptionsConfiguration
    });


    ctx = document.getElementById('lineChartExampleWithNumbersAndGrid').getContext("2d");

    gradientStroke = ctx.createLinearGradient(500, 0, 100, 0);
    gradientStroke.addColorStop(0, '#18ce0f');
    gradientStroke.addColorStop(1, chartColor);

    gradientFill = ctx.createLinearGradient(0, 170, 0, 50);
    gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");
    gradientFill.addColorStop(1, hexToRGB('#18ce0f', 0.4));

    chartEveryMonth = new Chart(ctx, {
      type: 'line',
      responsive: true,
      data: {
        labels: dataChartEveryMonth.labels,
        datasets: [{
          label: "(W)EveryMonth",
          borderColor: "#18ce0f",
          pointBorderColor: "#FFF",
          pointBackgroundColor: "#18ce0f",
          pointBorderWidth: 2,
          pointHoverRadius: 4,
          pointHoverBorderWidth: 1,
          pointRadius: 4,
          fill: true,
          backgroundColor: gradientFill,
          borderWidth: 2,
          data: dataChartEveryMonth.dataContent
        }]
      },
      options: gradientChartOptionsConfigurationWithNumbersAndGrid
    });

    var e = document.getElementById("barChartSimpleGradientsNumbers").getContext("2d");

    gradientFill = ctx.createLinearGradient(0, 170, 0, 50);
    gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");
    gradientFill.addColorStop(1, hexToRGB('#2CA8FF', 0.6));

    var a = {
      type: "bar",
      data: {
        labels: dataChartEveryYear.labels,
        datasets: [{
          label: "(W)EveryYear",
          backgroundColor: gradientFill,
          borderColor: "#2CA8FF",
          pointBorderColor: "#FFF",
          pointBackgroundColor: "#2CA8FF",
          pointBorderWidth: 2,
          pointHoverRadius: 4,
          pointHoverBorderWidth: 1,
          pointRadius: 4,
          fill: true,
          borderWidth: 1,
          data: dataChartEveryYear.dataContent
        }]
      },
      options: {
        maintainAspectRatio: false,
        legend: {
          display: false
        },
        tooltips: {
          bodySpacing: 4,
          mode: "nearest",
          intersect: 0,
          position: "nearest",
          xPadding: 10,
          yPadding: 10,
          caretPadding: 10
        },
        responsive: 1,
        scales: {
          yAxes: [{
            gridLines: 0,
            gridLines: {
              zeroLineColor: "transparent",
              drawBorder: false
            }
          }],
          xAxes: [{
            display: 0,
            gridLines: 0,
            ticks: {
              display: false
            },
            gridLines: {
              zeroLineColor: "transparent",
              drawTicks: false,
              display: false,
              drawBorder: false
            }
          }]
        },
        layout: {
          padding: {
            left: 0,
            right: 0,
            top: 15,
            bottom: 15
          }
        }
      }
    };
    chartEveryYear = new Chart(e, a);
  }
};

function pre_processChartData (chartData, resetCurrent, resetEveryDay, resetEveryMonth, resetEveryYear) {
  if (resetCurrent) {
    dataChartCurrent.labels=[];
    dataChartCurrent.dataContent=[];
  }
  if (resetEveryDay) {
    dataChartEveryDay.labels=[];
    dataChartEveryDay.dataContent=[];
  }
  if (resetEveryMonth) {
    dataChartEveryMonth.labels=[];
    dataChartEveryMonth.dataContent=[];
  }
  if (resetEveryYear) {
    dataChartEveryYear.labels=[];
    dataChartEveryYear.dataContent=[];
  }
  if(chartData.dataChartCurrent) {
    chartData.dataChartCurrent.forEach(function(element) {
      var d = new Date(element.TimeGet);
      d.setTime(d.getTime() + d.getTimezoneOffset()*60*1000);
      dataChartCurrent.labels.push(d.getHours().toString()+':'+d.getMinutes().toString()+':'+d.getSeconds().toString());
      dataChartCurrent.dataContent.push(element.Pac);
    });
  }
  if(chartData.dataChartEveryDay) {
    chartData.dataChartEveryDay.forEach(function(element) {
      var d = new Date(element.TimeGet);
      d.setTime(d.getTime() + d.getTimezoneOffset()*60*1000);
      dataChartEveryDay.labels.push(d.getDate().toString()+'/'+(d.getMonth()+1).toString()+'/'+d.getFullYear().toString());
      dataChartEveryDay.dataContent.push(element.EToday);
    });
  }
  if(chartData.dataChartEveryMonth) {
    chartData.dataChartEveryMonth.forEach(function(element) {
      var d = new Date(element.TimeGet);
      d.setTime(d.getTime() + d.getTimezoneOffset()*60*1000);
      dataChartEveryMonth.labels.push((d.getMonth()+1).toString()+'/'+d.getFullYear().toString());
      dataChartEveryMonth.dataContent.push(element.EToday);
    });
  }
  if(chartData.dataChartEveryYear) {
    chartData.dataChartEveryYear.forEach(function(element) {
      var d = new Date(element.TimeGet);
      d.setTime(d.getTime() + d.getTimezoneOffset()*60*1000);
      dataChartEveryYear.labels.push(d.getFullYear().toString());
      dataChartEveryYear.dataContent.push(element.EToday);
    });
  }
}

$(document).ready(function() {
  var socket = io();
  // Javascript method's body can be found in assets/js/demos.js

  socket.on("server-send-collected-today", function(data) {
    if (data) {
      var d=new Date();
      d.setTime(d.getTime() + d.getTimezoneOffset()*60*1000 + 7*3600*1000);
      var label=d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();
      console.log("da nhan du lieu moi");
      if (chartCurrent.data.labels.length!==0) {
        var latestLabel = chartCurrent.data.labels[chartCurrent.data.labels.length-1]
        var latestHour = latestLabel.slice(0, latestLabel.indexOf(':'));
        var latestMinute = latestLabel.slice(latestLabel.indexOf(':')+1, latestLabel.indexOf(':', latestLabel.indexOf(':')+1))
        
        //new suit data (new minute updated)
        if (d.getHours() >= latestHour && d.getMinutes() > latestMinute) {
          console.log('new minute');
          chartCurrent.data.labels.push(label);
          chartCurrent.data.datasets[0].data.push(data.Pac);
          chartCurrent.update();
        }
        //new day come
        if (d.getHours() < latestHour) {
          console.log('new day come');
          while (chartCurrent.data.labels.length) {
            chartCurrent.data.labels.pop();
            chartCurrent.data.datasets[0].data.pop();
          }
          chartCurrent.data.labels.push(label);
          chartCurrent.data.datasets[0].data.push(data.Pac);
          chartCurrent.update();
        }
        // new data but same minute
        if (d.getHours() >= latestHour && d.getMinutes() == latestMinute) {
          console.log('same minute');
          //get rid of old data same minute
          chartCurrent.data.labels.pop();
          chartCurrent.data.datasets[0].data.pop();
          //push new data
          chartCurrent.data.labels.push(label);
          chartCurrent.data.datasets[0].data.push(data.Pac);
          chartCurrent.update();
        }
      } else {
        console.log("first of day");
        chartCurrent.data.labels.push(label);
        chartCurrent.data.datasets[0].data.push(data.Pac);
        chartCurrent.update();
      }
    }
  });

  /***************************init all chart on dashboard*************************/
  var NodeID = $("#selectNodes").val();
  socket.emit("client-send-init-node", NodeID);

  socket.on("server-send-init-chart", function(data){
    pre_processChartData(data, true, true, true, true);
    userChart.initDashboardPageCharts(dataChartCurrent, dataChartEveryDay, dataChartEveryMonth, dataChartEveryYear)
  });

  /****************************renew all chart on dashboard**********************/
  $('#selectNodes').on('change',function(){
    //get NodeID and send server
    NodeID = $(this).val();
    socket.emit("client-send-renew-node", NodeID);

    //change status on dashboard
    $('#selectPowEveryDay') //clear all option first
        .empty()
    ;
    $("#optionSelectedPowEveryDay").html("");

    $('#selectPowEveryMonth') //clear all option first
        .empty()
    ;
    $("#optionSelectedPowEveryMonth").html("");

    //clear all data chart
    userChart.resetAllChart();
  });

  socket.on("server-send-renew-all-chart", function(data){
    pre_processChartData(data, true, true, true, true);
    userChart.updateAllChart();
  });

  /******************************renew chart every day***********************/
  socket.on("server-send-init-everyDay", function(monthsYears) { //add option
    $("#optionSelectedPowEveryDay").html("");
    if(monthsYears) {
      var html = "";
      html += monthsYears[0].monthTime + '/' + monthsYears[0].yearTime;
      $("#optionSelectedPowEveryDay").html(html);

      $('#selectPowEveryDay') //clear all option first
          .empty()
      ;

      monthsYears.forEach(function(monthYear) {
        $('#selectPowEveryDay').append('<option value="'+monthYear.monthTime + '/' + monthYear.yearTime+'" class="dropdown-item">'+monthYear.monthTime + '/' + monthYear.yearTime+'</option>')
      });
    } 
  });

  $('#selectPowEveryDay').on('change',function(){ //option change
    var content = $(this).val();
    $("#optionSelectedPowEveryDay").html(content);

    //reset all value in chart before change
    userChart.resetChartEveryDay();

    NodeID = $("#selectNodes").val();

    var dataSend = {NodeID: NodeID, time: {month: content.slice(0, content.indexOf('/')), year: content.slice(content.indexOf('/')+1)}};
    socket.emit("client-send-selected-monthYear", dataSend);
  });

  socket.on("server-send-renew-chartEveryDay", function(data) { //server send
    pre_processChartData(data, false, true, false, false);
    //console.log(chartEveryDay);
    userChart.updateChartEveryDay();
  });

  /******************************renew chart every month***********************/
  socket.on("server-send-init-everyMonth", function(monthsYears) { //add option
    $("#optionSelectedPowEveryMonth").html("");
    if(monthsYears) {
      var html = "";
      html += monthsYears[0].yearTime;
      $("#optionSelectedPowEveryMonth").html(html);

      $('#selectPowEveryMonth') //clear all option first
          .empty()
      ;

      monthsYears.forEach(function(monthYear) {
        $('#selectPowEveryMonth').append('<option value="'+ monthYear.yearTime+'" class="dropdown-item">'+monthYear.yearTime+'</option>')
      });
    } 
  });

  $('#selectPowEveryMonth').on('change',function(){ //option change
    var content = $(this).val();
    $("#optionSelectedPowEveryMonth").html(content);

    //reset all value in chart before change
    userChart.resetChartEveryMonth();

    NodeID = $("#selectNodes").val();

    socket.emit("client-send-selected-year", {NodeID: NodeID, time: {year: content}});
  });

  socket.on("server-send-renew-chartEveryMonth", function(data) { //server send
    pre_processChartData(data, false, false, true, false);
    userChart.updateChartEveryMonth();
  });
 
  /********************************update current data*****************************/
  socket.on("server-send-current-data", function(data) {
    if (data) {
      var dLocal = new Date();
      var d = new Date(data.TimeGet);
      d.setTime(d.getTime() - dLocal.getTimezoneOffset()*60*1000 - 7*3600*1000);
      //console.log((d.getHours()).toString()+':'+d.getMinutes().toString()+':'+d.getSeconds().toString());
      $("#TimeGet").html((d.getHours()).toString()+':'+d.getMinutes().toString()+':'+d.getSeconds().toString());
      $("#PV_Vol").html(data.PV_Vol);
      $("#PV_Amp").html(data.PV_Amp);
      $("#Bus").html(data.Bus);
      $("#AC_Vol").html(data.AC_Vol);
      $("#AC_Hz").html(data.AC_Hz);
      $("#Tem").html(data.Tem);
      $("#Pac").html(data.Pac);
      $("#EToday").html(data.EToday);
      $("#EAll").html(data.EAll);
      if (data.StatusConnect) {
        $("#StatusConnect").html("Connected");
      } else {
        $("#StatusConnect").html("Disconnected");
      }
    } else {
      $("#TimeGet").html("");
      $("#PV_Vol").html("");
      $("#PV_Amp").html("");
      $("#Bus").html("");
      $("#AC_Vol").html("");
      $("#AC_Hz").html("");
      $("#Tem").html("");
      $("#Pac").html("");
      $("#EToday").html("");
      $("#EAll").html("");
      $("#StatusConnect").html("");
    }
  });
  socket.on("server-send-status-connect", function(data) {
    if (data) {
      $("#StatusConnect").html("Connected");
    } else {
      $("#StatusConnect").html("Disconnected");
    }
  });
});