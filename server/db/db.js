var mysql      = require('mysql');
	
var pool = mysql.createPool({
    connectionLimit: 5,
    host     : 'localhost',
  	user     : 'ceec_solar_manager',
  	password : 'qwerty123456789',
  	database : 'ceec_solar'
});

exports.checkLogin = function (username, pass) {
	return new Promise (function (resolve, reject) {
		pool.query("select * from users where ID=upper('"+username+"') and Pass=AES_ENCRYPT('"+pass+"', 'qwerty123456');", function(err, rows, fields) { 
			if (err) reject(err); 
			if(rows.length>=1) {
				resolve(rows[0]);
			} else {
				resolve(false);
			}
		});
	});
}

exports.checkUsername = function (username) {
	return new Promise (function (resolve, reject) {
		pool.query("select * from users where ID=upper('"+username+"');", function(err, rows, fields) { 
			if (err) reject(err); 
			if(rows.length>=1) {
				resolve(true);
			} else {
				reject(false);
			}
		});
	});
}

exports.insertUser = function (username, pass) {
	return new Promise (function (resolve, reject) {
		pool.query("insert into Users (ID, Pass, NumNode) values (upper('"+username+"'), AES_ENCRYPT('"+pass+"', 'qwerty123456'), 0);", function(err, rows, fields) { 
			if (err) reject(err); 
			resolve(true);
		});
	});
}

exports.deleteUser = function (username, pass) {
	return new Promise (function (resolve, reject) {
		pool.query("delete from Users where ID=upper('"+username+"') and Pass=AES_ENCRYPT('"+pass+"', 'qwerty123456');", function(err, rows, fields) { 
			if (err) reject(err);
			resolve(true);
		});
	});
}

exports.infoUser = function (username) {
	return new Promise (function (resolve, reject) {
		pool.query("select * from users where ID=upper('"+username+"');", function(err, rows, fields) { 
			if (err) reject(err); 
			if(rows.length>=1){
				resolve(rows[0]);
			} else {
				resolve(false);
			}
		});
	});
}

exports.listNodes = function (username) {
	return new Promise (function (resolve, reject) {
		pool.query("select * from nodes where ID=upper('"+username+"');", function(err, rows, fields) { 
			if (err) reject(err); 
			resolve(rows);
		});
	});
}

exports.listMonthsYears = function (NodeID) {
	return new Promise (function (resolve, reject) {
		pool.query("SELECT DISTINCT YEAR(TimeGet) as yearTime, MONTH(TimeGet) as monthTime FROM CollectedData where NodeID='"+NodeID+"';", function(err, rows, fields) { 
			if (err) reject(err); 
			if(rows.length>=1) {
				resolve(rows);
			} else {
				resolve(false);
			}
		});
	});
}

exports.listYears = function (NodeID) {
	return new Promise (function (resolve, reject) {
		pool.query("SELECT DISTINCT YEAR(TimeGet) as yearTime FROM CollectedData where NodeID='"+NodeID+"';", function(err, rows, fields) { 
			if (err) reject(err);
			if(rows.length>=1) {
				resolve(rows);
			} else {
				resolve(false);
			}
		});
	});
}

exports.getCurrentData = function (NodeID) {
	return new Promise (function (resolve, reject) {
		pool.query("select * from currentdata where NodeID=upper('"+NodeID+"');", function(err, rows, fields) { 
			if (err) reject(err); 
			if(rows.length>=1) {
				let status=new Uint8Array(rows[0].StatusConnect);
				rows[0].StatusConnect=status[0];
				resolve(rows[0]);
			} else {
				resolve(false);
			}
		});
	});
}

exports.getCollectedDataSpecDay = function (dateTime, NodeID) {
	return new Promise (function (resolve, reject) {
		pool.query("select ROUND(AVG(Pac), 1) as Pac, TimeGet from collecteddata where NodeID=upper('"+NodeID+"') and year(TimeGet)="+dateTime.year+" and month(TimeGet)="+dateTime.month+" and day(TimeGet)="+dateTime.day+" group by hour(TimeGet), minute(TimeGet) order by TimeGet ASC;", function(err, rows, fields) { 
			if (err) reject(err);
			if(rows.length>=1) {
				resolve(rows);
			} else {
				resolve(false);
			}
		});
	});
}

exports.getCollectedDataEveryDay = function (dateTime, NodeID) {
	return new Promise (function (resolve, reject) {
		pool.query("select ROUND(AVG(Pac), 1) as Pac, TimeGet from collecteddata where NodeID=upper('"+NodeID+"') and year(TimeGet)="+dateTime.year+" and month(TimeGet)="+dateTime.month+" group by day(TimeGet) order by TimeGet ASC;", function(err, rows, fields) { 
			if (err) reject(err);
			if(rows.length>=1) {
				resolve(rows);
			} else {
				resolve(false);
			}
		});
	});
}

exports.getCollectedDataEveryMonth = function (dateTime, NodeID) {
	return new Promise (function (resolve, reject) {
		pool.query("select ROUND(AVG(Pac), 1) as Pac, TimeGet from collecteddata where NodeID=upper('"+NodeID+"') and year(TimeGet)="+dateTime.year+" group by month(TimeGet) order by TimeGet ASC;", function(err, rows, fields) { 
			if (err) reject(err);
			if(rows.length>=1) {
				resolve(rows);
			} else {
				resolve(false);
			}
		});
	});
}

exports.getCollectedDataEveryYear = function (NodeID) {
	return new Promise (function (resolve, reject) {
		pool.query("select ROUND(AVG(Pac), 1) as Pac, TimeGet from collecteddata where NodeID=upper('"+NodeID+"') group by year(TimeGet) order by TimeGet ASC;", function(err, rows, fields) { 
			if (err) reject(err);
			if(rows.length>=1) {
				resolve(rows);
			} else {
				resolve(false);
			}
		});
	});
}

var aesjs = require('aes-js');
// An example 128-bit key
var key = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 ];

function decodePassFromAes128 (rawPass) {
	// The cipher-block chaining mode of operation maintains internal
	// state, so to decrypt a new instance must be instantiated.
	// The initialization vector (must be 16 bytes)
	var iv = rawPass.slice(32,48);

	var aesCbcFront = new aesjs.ModeOfOperation.cbc(key, iv);

	var decryptedBytes= aesCbcFront.decrypt(rawPass.slice(0,16));
	// Convert our bytes back into text
	var decryptedPassFront = aesjs.utils.utf8.fromBytes(decryptedBytes);

	var aesCbcEnd = new aesjs.ModeOfOperation.cbc(key, iv);

	decryptedBytes= aesCbcEnd.decrypt(rawPass.slice(16,32));
	// Convert our bytes back into text
	var decryptedPassEnd = aesjs.utils.utf8.fromBytes(decryptedBytes);
	
	PassClientSend=decryptedPassFront.slice(0,15)+decryptedPassEnd.slice(0,15);

	var endPos = PassClientSend.indexOf("*");
	if (endPos<0) {
		PassClientSend=PassClientSend.slice(0,30);
	} else {
		PassClientSend=PassClientSend.slice(0,endPos);
	}
	return PassClientSend;
}

exports.checkLoginRawPass = function (username, pass) {
	var newPass=decodePassFromAes128(pass);
	return new Promise (function (resolve, reject) {
		pool.query("select * from users where ID=upper('"+username+"') and Pass=AES_ENCRYPT('"+newPass+"', 'qwerty123456');", function(err, rows, fields) { 
			if (err) reject(err); 
			if(rows.length>=1) {
				resolve(true);
			} else {
				resolve(false);
			}
		});
	});
}

exports.updateCurrentData = function (username, NodeID, data) {
	pool.query("update currentdata set TimeGet='"+data.TimeGet+"', PV_Vol="+data.PV_Vol+", \
		PV_Amp="+data.PV_Amp+", Bus="+data.Bus+", AC_Vol="+data.AC_Vol+", AC_Hz="+data.AC_Hz+", \
		Tem="+data.Tem+", Pac="+data.Pac+", EToday="+data.EToday+", EAll="+data.EAll+", \
		StatusConnect="+data.StatusConnect+" \
		where ID='"+username+"' and NodeID='"+NodeID+"';", function(err, result) {
			if(err) throw err;
	});
}

exports.updateStatusConnect = function (NodeID, status) {
	pool.query("update currentdata set StatusConnect="+status+" where NodeID='"+NodeID+"';", function(err,result) {
		if(err) throw err;
	});
}

exports.insertCollectedData = function (username, NodeID, data) {
	pool.query("insert into collecteddata (ID, TimeGet, Pac, NodeID) values\
		(upper('"+username+"'), '"+data.TimeGet+"', "+data.Pac+", upper('"+NodeID+"'));", function(err, result) {
		
		if(err) throw err;
	});
}



/*
userModel.checkLogin(username,password).then(function (result) {
  return done(null, username, {message: 'Login in Successfully'});
}, function (err) {
  return done(null, false, {message: 'Login fail'});
});
*/