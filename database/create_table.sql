create table Users (
	ID char(30) primary key,
	Pass varbinary(50) not null,
	Company varchar(50),
	Email varchar(50),
	FirstName varchar(30),
	LastName varchar(30),
	Address varchar(50), 
	City varchar(30), 
	About varchar(500), 
	LinkInfo varchar(100), 
	Slogan varchar(500), 
	Country varchar(50), 
	Avatar varchar(500), 
	CoverAvatar varchar(500)
);
create table Nodes (
	ID char(30) not null,
	NodeID char(50) PRIMARY KEY,
	NodeName varchar(50),
	CONSTRAINT FK_Nodes_Users FOREIGN KEY (ID) REFERENCES Users (ID)
);
create table CurrentData (
	ID char(30) not null,
	TimeGet datetime,
	PV_Vol float,
	PV_Amp float,
	Bus float,
	AC_Vol float,
	AC_Hz float,
	Tem float,
	Pac float,
	EToday float,
	EAll float,
	StatusConnect bit,
	NodeID char(50) not null,
    CONSTRAINT PK_Current PRIMARY KEY (ID, NodeID),
	CONSTRAINT FK_Current_Nodes FOREIGN KEY (NodeID) REFERENCES Nodes (NodeID),
	CONSTRAINT FK_Current_Users FOREIGN KEY (ID) REFERENCES Users (ID)
);
create table CollectedData (
	IndexData int(50) primary key,
	ID char(30) not null,
	TimeGet datetime,
	Pac float,
	NodeID char(50) not null,
	CONSTRAINT FK_Collected_Nodes FOREIGN KEY (NodeID) REFERENCES Nodes (NodeID),
	CONSTRAINT FK_Collected_Users FOREIGN KEY (ID) REFERENCES Users (ID)
);

#SET SQL_SAFE_UPDATES = 0;
#insert into Users (ID, Pass, NumNode) values (upper('ceec'), AES_ENCRYPT('ce.uit.edu.vn', 'qwerty123456'), 1);
#select * from users where ID=upper('ceec') and Pass=AES_ENCRYPT('ce.uit.edu.vn', 'qwerty123456');