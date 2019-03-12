
var dataChartCurrent={labels:[], dataContent:[]},
    dataChartEveryDay={labels:[], dataContent:[]},
    dataChartEveryMonth={labels:[], dataContent:[]},
    dataChartEveryYear={labels:[], dataContent:[]};

var chartCurrent, chartEveryDay, chartEveryMonth, chartEveryYear;

userChart = {
  addChartEveryDay: function  (data) {
    chartEveryDay.data.labels.push(data.TimeGet);
    chartEveryDay.data.datasets.forEach((dataset) => {
        dataset.data.push(data.Pac);
    });
    chartEveryDay.update();
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

    var chartCurrent = new Chart(ctx, {
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
    var chartEveryYear = new Chart(e, a);
  }
};

function pre_processChartData (chartData) {
  dataChartCurrent={labels:[], dataContent:[]},
  dataChartEveryDay={labels:[], dataContent:[]},
  dataChartEveryMonth={labels:[], dataContent:[]},
  dataChartEveryYear={labels:[], dataContent:[]};
  if(chartData.dataChartCurrent) {
    chartData.dataChartCurrent.forEach(function(element) {
      var d = new Date(element.TimeGet);
      dataChartCurrent.labels.push(d.getHours().toString()+':'+d.getMinutes().toString()+':'+d.getSeconds().toString());
      dataChartCurrent.dataContent.push(element.Pac);
    });
  }
  if(chartData.dataChartEveryDay) {
    chartData.dataChartEveryDay.forEach(function(element) {
      var d = new Date(element.TimeGet);
      dataChartEveryDay.labels.push(d.getDate().toString()+'/'+(d.getMonth()+1).toString()+'/'+d.getFullYear().toString());
      dataChartEveryDay.dataContent.push(element.Pac);
    });
  }
  if(chartData.dataChartEveryMonth) {
    chartData.dataChartEveryMonth.forEach(function(element) {
      var d = new Date(element.TimeGet);
      dataChartEveryMonth.labels.push((d.getMonth()+1).toString()+'/'+d.getFullYear().toString());
      dataChartEveryMonth.dataContent.push(element.Pac);
    });
  }
  if(chartData.dataChartEveryYear) {
    chartData.dataChartEveryYear.forEach(function(element) {
      var d = new Date(element.TimeGet);
      dataChartEveryYear.labels.push(d.getFullYear().toString());
      dataChartEveryYear.dataContent.push(element.Pac);
    });
  }
}

$(document).ready(function() {
  var socket = io.connect('http://localhost:3000');
  // Javascript method's body can be found in assets/js/demos.js

  var NodeID = $("#selectNodes").val();
  socket.emit("client-send-init-node", NodeID);

  $('#selectNodes').on('change',function(){
    NodeID = $(this).val();
    socket.emit("client-send-init-node", NodeID);
  });

  $('#selectPowEveryDay').on('change',function(){
    var content = $(this).val();
    $("#optionSelectedPowEveryDay").html(content);
  });

  $('#selectPowEveryMonth').on('change',function(){
    var content = $(this).val();
    $("#optionSelectedPowEveryMonth").html(content);
  });
 
  socket.on("server-send-init-everyDay", function(monthsYears) {
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

  socket.on("server-send-init-everyMonth", function(monthsYears) {
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

  socket.on("server-send-init-chart", function(data){
    pre_processChartData(data);
    userChart.initDashboardPageCharts(dataChartCurrent, dataChartEveryDay, dataChartEveryMonth, dataChartEveryYear)
  });

  socket.on("server-send-current-data", function(data) {
    if (data) {
      var d = new Date(data.TimeGet);
      $("#TimeGet").html(d.getHours().toString()+':'+d.getMinutes().toString()+':'+d.getSeconds().toString());
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
});