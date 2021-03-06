var mysql      = require('mysql');
	
var pool = mysql.createPool({
    connectionLimit: 20,
    host     : 'us-cdbr-iron-east-03.cleardb.net',
  	user: 'bf18d06e29280a', 
    password: '6789a095',
    database: 'heroku_93c0d4bea167c0e'
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
				resolve(false);
			}
		});
	});
}

exports.insertUser = function (username, pass, email) {
	pool.query("insert into Users (ID, Pass, Email) values (upper('"+username+"'), AES_ENCRYPT('"+pass+"', 'qwerty123456'), '"+email+"');", function(err, rows, fields) { 
		if (err) throw err;
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

exports.updateInfoUser = function (username, data) {
	return new Promise (function (resolve, reject) {
		pool.query("update users\
			set Company= '"+data.company+"', Email= '"+data.email+"', FirstName= '"+data.firstName+"', LastName= '"+data.lastName+"', \
			Address= '"+data.address+"', City= '"+data.city+"', About= '"+data.about+"', LinkInfo= '"+data.linkInfo+"', \
			Slogan= '"+data.slogan+"', Country= '"+data.country+"', Avatar= '"+data.avatar+"', \
			CoverAvatar= '"+data.coverAvatar+"' where ID= '"+username+"';", function(err, result) { 
			if (err) reject(err);
			resolve(true);
		});
	});
};

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
		pool.query("SELECT DISTINCT YEAR(TimeGet) as yearTime, MONTH(TimeGet) as monthTime FROM Statistics where NodeID='"+NodeID+"';", function(err, rows, fields) { 
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
		pool.query("SELECT DISTINCT YEAR(TimeGet) as yearTime FROM Statistics where NodeID='"+NodeID+"';", function(err, rows, fields) { 
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

exports.getStatisticsDataSpecDay = function (dateTime, NodeID) {
	return new Promise (function (resolve, reject) {
		pool.query("select ROUND(AVG(EToday), 1) as EToday, TimeGet from Statistics where NodeID=upper('"+NodeID+"') and year(TimeGet)="+dateTime.year+" and month(TimeGet)="+dateTime.month+" and day(TimeGet)="+dateTime.day+" group by hour(TimeGet), minute(TimeGet) order by TimeGet ASC;", function(err, rows, fields) { 
			if (err) reject(err);
			if(rows.length>=1) {
				resolve(rows[0]);
			} else {
				resolve(false);
			}
		});
	});
}

exports.getCollectedDataEveryDay = function (dateTime, NodeID) {
	return new Promise (function (resolve, reject) {
		pool.query("select ROUND(AVG(EToday), 1) as EToday, TimeGet from Statistics where NodeID=upper('"+NodeID+"') and year(TimeGet)="+dateTime.year+" and month(TimeGet)="+dateTime.month+" group by day(TimeGet) order by TimeGet ASC;", function(err, rows, fields) { 
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
		pool.query("select ROUND(AVG(EToday), 1) as EToday, TimeGet from Statistics where NodeID=upper('"+NodeID+"') and year(TimeGet)="+dateTime.year+" group by month(TimeGet) order by TimeGet ASC;", function(err, rows, fields) { 
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
		pool.query("select ROUND(AVG(EToday), 1) as EToday, TimeGet from Statistics where NodeID=upper('"+NodeID+"') group by year(TimeGet) order by TimeGet ASC;", function(err, rows, fields) { 
			if (err) reject(err);
			if(rows.length>=1) {
				resolve(rows);
			} else {
				resolve(false);
			}
		});
	});
}

exports.getLatestEToday = function (dateTime, NodeID) {
	return new Promise (function (resolve, reject) {
		pool.query("select * from Statistics where NodeID=upper('"+NodeID+"') and day(TimeGet)="+dateTime.day+" and month(TimeGet)="+dateTime.month+" and year(TimeGet)="+dateTime.year+";", function(err, rows, fields) { 
			if (err) reject(err);
			if(rows.length>=1) {
				resolve(rows[0]);
			} else {
				resolve(false);
			}
		});
	});
}

exports.updateEToday = function (IndexData, data) {
	pool.query("update Statistics set TimeGet='"+data.TimeGet+"', EToday="+data.EToday+" where IndexData="+IndexData+";", function(err,result) {
		if(err) throw err;
	});
}

exports.insertStatistics = function (username, NodeID, data) {
	return new Promise (function (resolve, reject) {
		pool.query("insert into Statistics (ID, TimeGet, EToday, NodeID) values\
			(upper('"+username+"'), '"+data.TimeGet+"', "+data.EToday+", upper('"+NodeID+"'));", function(err, result) {
			if (err) reject(err);
			resolve(true);
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
	return new Promise (function (resolve, reject) {
		pool.query("insert into collecteddata (ID, TimeGet, Pac, NodeID) values\
			(upper('"+username+"'), '"+data.TimeGet+"', "+data.Pac+", upper('"+NodeID+"'));", function(err, result) {
			if (err) reject(err);
			resolve(true);
		});
	});
}



/*
userModel.checkLogin(username,password).then(function (result) {
  return done(null, username, {message: 'Login in Successfully'});
}, function (err) {
  return done(null, false, {message: 'Login fail'});
});
*/