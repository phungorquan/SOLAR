var express = require("express");
var app = express();
var server = require("http").createServer(app);
var io = require("socket.io").listen(server);
var fs = require("fs");
server.listen(process.env.PORT || 3000);

io.sockets.on('connection', function (socket) {
	
  console.log("Co nguoi connect ne");
  
  //io.sockets.emit('serverguitinnhan', { noidung: "okbaby" });
  
  socket.on('client_send', function (data) {
	// emit toi tat ca moi nguoi
	//io.sockets.emit('serverguitinnhan', { noidung: data });
		console.log("ServernhantuAndroid :" + data.id + "-" + data.pass);
	// emit tới máy nguoi vừa gửi
	//socket.emit('server_send', { doituongnoidung: "I'm server Hello Android" });
  });
  
  socket.on("disconnect",function(){
		console.log("out");
	});
});