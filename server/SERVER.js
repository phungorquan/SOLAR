var express = require('express'), // express framework
    session = require('express-session'), // session middleware
    cookieParser = require('cookie-parser'), // cookie middleware
    MySQLStore = require('connect-mysql')(session), // mysql session store
    options = {
      config: {
        user: 'ceec_solar_manager', 
        password: 'qwerty123456789',
        database: 'ceec_solar' 
      }
    },
    app = express();
var sessionStore = new MySQLStore(options);

app.use(express.static("./public"));
app.set("view engine","ejs");
app.set("views", "./views");

var server = require("http").Server(app);

//database
var db = require("./db/db");

app.use(cookieParser());

app.use(session({
    secret: 'keyboard cat',
    resave: false,
    saveUninitialized: true,
    store: sessionStore // Change the express session store
}));

//body parser and pass
var bodyParser = require('body-parser');
var passport = require('passport');
var localStrategy = require('passport-local').Strategy;

app.use(bodyParser.urlencoded({extended: false}));

app.use(passport.initialize());
app.use(passport.session({secret:'keyboard cat',  key:'connect.sid', store: sessionStore}));

passport.serializeUser((user, done) => {
    done(null, user)
})

passport.deserializeUser(function(user, done) {
    done(null, user);
});


//authenticate passport
passport.use('local', new  localStrategy({passReqToCallback : true}, (req, username, password, done) => {

    loginAttempt();

    async function loginAttempt() {
        try{
            var ID="";
            infoUser= await db.checkLogin(username,password);
            console.log("login success");
            return done(null, {ID: infoUser.ID});
        }
        catch(e){throw (e);return done(new Error("query database"));}
        };
    }
));

//socket io and pass port
var io = require("socket.io")(server);
passportSocketIo = require("passport.socketio");

//With Socket.io >= 1.0
io.use(passportSocketIo.authorize({
  cookieParser: cookieParser,       // the same middleware you registrer in express
  //connect.sid is default
  key:          'connect.sid',       // the name of the cookie where express/connect stores its session_id
  secret:       'keyboard cat',      // the session_secret to parse the cookie
  store:        sessionStore,        // we NEED to use a sessionstore. no memorystore please
  success:      onAuthorizeSuccess,  // *optional* callback on success - read more below
  fail:         onAuthorizeFail,     // *optional* callback on fail/error - read more below
}));

function onAuthorizeSuccess(data, accept){
  //console.log('successful connection to socket.io');
  //console.log("day la socket: "+data.id);
  // The accept-callback still allows us to decide whether to
  // accept the connection or not.
  //accept(null, true);
 
  // OR
 
  // If you use socket.io@1.X the callback looks different
  //console.log(data);
  accept();
}
 
function onAuthorizeFail(data, message, error, accept){
  //console.log('failed connection to socket.io:', message);
 
  // We use this callback to log all of our failed connections.
  //accept(null, false);
  //console.log(data);

  // OR
  if(data.headers.origin==="Arduino") {
    accept();
  }
  // If you use socket.io@1.X the callback looks different
  // If you don't want to accept the connection
  if(error)
    accept(new Error(message));
  // this error will be sent to the user as a special error-package
  // see: http://socket.io/docs/client-api/#socket > error-object
}

io.on("connection", function(socket) {
    //init
    socket.auth=false;
    socket.clientType="web_client";
    socket.Phong="null";
    console.log(socket.id + " connected");
    socket.on("disconnect", function() {
        console.log(socket.id + " disconnected");
        if (socket.clientType==="esp_client" && socket.Phong!=="null") {
          db.updateStatusConnect(socket.Phong, 0);
          io.sockets.in(socket.Phong).emit("server-send-status-connect", false);
        }
    });

    socket.on("text", function(data) {
        console.log(data);
    });

    socket.on("authentication", function(data) {
        authEsp();
        async function authEsp() {
            try{
                var ret= await db.checkLoginRawPass(data.username, data.password);
                
                if(ret) {
                    socket.auth=true;
                    socket.clientType="esp_client";
                    socket.Phong=data.NodeID;
                } else {
                    socket.disconnect();
                }
                
            }
            catch(e){
                throw (e);
                socket.disconnect();
            }
        };
    });
    socket.on("client-send-init-node", function(NodeID) { //data is NodeID
      if (NodeID) {
        processNodes();
      }
      async function processNodes () {
        try {
          //client leave room
          if(socket.Phong!=="null") {
            socket.leave(socket.Phong);
          }
          //join room
          socket.join(NodeID);
          socket.Phong=NodeID;

          db.updateStatusConnect(socket.Phong, 0);

          //get-send current data
          var currentData=await db.getCurrentData(NodeID);
          io.sockets.in(socket.Phong).emit("server-send-current-data", currentData);
          
          //get collected data today
          var d = new Date;
          var jsonDate = {year: d.getFullYear(), month: d.getMonth()+1, day: d.getDate()};

          var dataChartCurrent, dataChartEveryDay, dataChartEveryMonth, dataChartEveryYear;
          
          //data today average per minutes
          /* dataChartCurrent
            =false if there is no data found
            =[{TimeGet: ..., Pac: ...}, ....]
          */
          var result=await db.getCollectedDataSpecDay(jsonDate, NodeID);
          dataChartCurrent=result;

          //data everyday average
          /* dataChartEveryDay
            =false if there is no data found
            =[{TimeGet: ..., Pac: ...}, ....]
          */
          result=await db.listMonthsYears(NodeID);
          io.sockets.in(socket.Phong).emit("server-send-init-everyDay", result);
          if (result) {
            var monthsYears = {month: result[0].monthTime, year: result[0].yearTime};
            //everyDay per month
            result=await db.getCollectedDataEveryDay(monthsYears, NodeID);
          }
          dataChartEveryDay=result;

          //data everymonth average
          /* dataChartEveryMonth
            =false if there is no data found
            =[{TimeGet: ..., Pac: ...}, ....]
          */
          result=await db.listYears(NodeID);
          io.sockets.in(socket.Phong).emit("server-send-init-everyMonth", result);
          if(result) {
            var Years = {year: result[0].yearTime};
            //everyMonth per year
            result=await db.getCollectedDataEveryMonth(Years, NodeID);
          }
          dataChartEveryMonth=result;

          //data everyYear average
          /* dataChartEveryYear
            =false if there is no data found
            =[{TimeGet: ..., Pac: ...}, ....]
          */
          result=await db.getCollectedDataEveryYear(NodeID);
          dataChartEveryYear=result;
          io.sockets.in(socket.Phong).emit("server-send-init-chart", {dataChartCurrent: dataChartCurrent, dataChartEveryDay: dataChartEveryDay, dataChartEveryMonth: dataChartEveryMonth, dataChartEveryYear: dataChartEveryYear});
        }
        catch(err) {
          throw(err);
        }
      } 
    });

    socket.on("client-send-renew-node", function(NodeID) { //data is NodeID
      if (NodeID) {
        processNodes();
      }
      async function processNodes () {
        try {
          //client leave room
          if(socket.Phong!=="null") {
            socket.leave(socket.Phong);
          }
          //join room
          socket.join(NodeID);
          socket.Phong=NodeID;

          //get-send current data
          var currentData=await db.getCurrentData(NodeID);
          io.sockets.in(socket.Phong).emit("server-send-current-data", currentData);
          
          //get collected data today
          var d = new Date;
          var jsonDate = {year: d.getFullYear(), month: d.getMonth()+1, day: d.getDate()};

          var dataChartCurrent, dataChartEveryDay, dataChartEveryMonth, dataChartEveryYear;
          
          //data today average per minutes
          /* dataChartCurrent
            =false if there is no data found
            =[{TimeGet: ..., Pac: ...}, ....]
          */
          var result=await db.getCollectedDataSpecDay(jsonDate, NodeID);
          dataChartCurrent=result;

          //data everyday average
          /* dataChartEveryDay
            =false if there is no data found
            =[{TimeGet: ..., Pac: ...}, ....]
          */
          result=await db.listMonthsYears(NodeID);
          io.sockets.in(socket.Phong).emit("server-send-init-everyDay", result);
          if (result) {
            var monthsYears = {month: result[0].monthTime, year: result[0].yearTime};
            //everyDay per month
            result=await db.getCollectedDataEveryDay(monthsYears, NodeID);
          }
          dataChartEveryDay=result;

          //data everymonth average
          /* dataChartEveryMonth
            =false if there is no data found
            =[{TimeGet: ..., Pac: ...}, ....]
          */
          result=await db.listYears(NodeID);
          io.sockets.in(socket.Phong).emit("server-send-init-everyMonth", result);
          if(result) {
            var Years = {year: result[0].yearTime};
            //everyMonth per year
            result=await db.getCollectedDataEveryMonth(Years, NodeID);
          }
          dataChartEveryMonth=result;

          //data everyYear average
          /* dataChartEveryYear
            =false if there is no data found
            =[{TimeGet: ..., Pac: ...}, ....]
          */
          result=await db.getCollectedDataEveryYear(NodeID);
          dataChartEveryYear=result;
          io.sockets.in(socket.Phong).emit("server-send-renew-all-chart", {dataChartCurrent: dataChartCurrent, dataChartEveryDay: dataChartEveryDay, dataChartEveryMonth: dataChartEveryMonth, dataChartEveryYear: dataChartEveryYear});
        }
        catch(err) {
          throw(err);
        }
      } 
    });

    socket.on("esp_send_data",function(data) {
        if(socket.auth) {
            var d = new Date;
            var jsonDate = {year: d.getFullYear(), month: d.getMonth()+1, day: d.getDate()};
            var dformat =
              [d.getFullYear(),
               d.getMonth()+1,
               d.getDate()].join('-')+' '+
              [d.getHours(),
               d.getMinutes(),
               d.getSeconds()].join(':');
            var newData = {
                ID: data.username.toUpperCase(),
                TimeGet: dformat,
                NodeID: data.NodeID.toUpperCase(),
                PV_Vol: data.dataInvert[0],
                PV_Amp: data.dataInvert[1],
                Bus :data.dataInvert[2],
                AC_Vol :data.dataInvert[3],
                AC_Hz :data.dataInvert[4],
                Tem :data.dataInvert[5],
                Pac :data.dataInvert[6],
                EToday :data.dataInvert[7],
                EAll :data.dataInvert[8],
                StatusConnect :data.dataInvert[9],
                CurrentOrCollected: data.dataInvert[10]
            };
            if(newData.CurrentOrCollected===0) {
              io.sockets.in(newData.NodeID).emit("server-send-current-data", newData);
              db.updateCurrentData(newData.ID, newData.NodeID, newData);
            } else if(newData.CurrentOrCollected===1) {
              db.insertCollectedData(newData.ID, newData.NodeID, newData);
              db.getCollectedDataSpecDay(jsonDate, newData.NodeID).then(function (result) {
                  //just get newest data
                  io.sockets.in(newData.NodeID).emit("server-send-collected-today", result[result.length-1]);
              }, function (err) {
                  io.sockets.in(newData.NodeID).emit("server-send-collected-today", false);
              });
            }
        } else {
            socket.disconnect();
        }
    });

    //client send request renew chartEverDay
    socket.on("client-send-selected-monthYear", function(data) {
      var NodeID=data.NodeID;
      var monthYear=data.time;
      //data everyday average
      /* dataChartEveryDay
        =false if there is no data found
        =[{TimeGet: ..., Pac: ...}, ....]
      */
      getEveryDay();
      async function getEveryDay() {
        try {
          var dataChartEveryDay=await db.getCollectedDataEveryDay(monthYear, NodeID);
          socket.emit("server-send-renew-chartEveryDay", {dataChartCurrent: false, dataChartEveryDay: dataChartEveryDay, dataChartEveryMonth: false, dataChartEveryYear: false});
        } catch(err) {throw err;};
      };
    });

    //client send request renew chartEveryMonth
    socket.on("client-send-selected-year", function(data) {
      var NodeID=data.NodeID;
      var monthYear=data.time;
      //data everymonth average
      /* dataChartEveryMonth
        =false if there is no data found
        =[{TimeGet: ..., Pac: ...}, ....]
      */
      getEveryDay();
      async function getEveryDay() {
        try {
          var dataChartEveryMonth=await db.getCollectedDataEveryMonth(monthYear, NodeID);
          socket.emit("server-send-renew-chartEveryMonth", {dataChartCurrent: false, dataChartEveryDay: false, dataChartEveryMonth: dataChartEveryMonth, dataChartEveryYear: false});
        } catch(err) {throw err;};
      };
    });
});

app.get('/login', function(req, res, next) {
    if (req.isAuthenticated()) {
        res.redirect('/dashboard');
    }
    else {
        res.render("login");
    }
});

app.post('/login', passport.authenticate('local', {
    successRedirect: '/dashboard',
    failureRedirect: '/login',
    failureFlash: true
}));

app.get('/logout', function(req, res){
  req.logout();
  res.redirect('/');
});

app.get('/', function(req, res) {
  res.send("day la trang chu");
})

app.get('/dashboard', function(req, res) {
    if (req.isAuthenticated()) {
        nodes();
        async function nodes() {
            try{
                nodes= await db.listNodes(req.user.ID);
                monthsYears = await db.listMonthsYears(nodes[0].NodeID);
                years = await db.listYears(nodes[0].NodeID);
                res.render("dashboard", {nodes:nodes, monthsYears:monthsYears, years:years});
            }
            catch(e){throw (e);res.render("dashboard", {nodes:[], monthsYears:[], years:[]});}
        };
    }
    else {
        res.redirect('/login');
    }
});

app.get('/nodes', function(req, res) {
    if (req.isAuthenticated()) {
        nodes();
        async function nodes() {
            try{
                nodes= await db.listNodes(req.user.ID);
                if(nodes)
                    res.render("nodes", {nodes:nodes});
            }
            catch(e){throw (e);}
        };
    }
    else {
        res.redirect('/login');
    }
});

app.get('/user', function(req, res) {
    if (req.isAuthenticated()) {
        user();
        async function user() {
            try{
                infoUser=await db.infoUser(req.user.ID);
                delete infoUser.Pass;
                if (infoUser)
                    res.render("user", {infoUser: infoUser});
            }
            catch(e){throw (e);}
        };
    }
    else {
        res.redirect('/login');
    }
});

app.get('/notifications', function(req, res) {
    if (req.isAuthenticated()) {
        res.render('notifications');
    }
    else {
        res.redirect('/login');
    }
});

server.listen(3000);