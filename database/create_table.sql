create table Users (
	ID char(30) primary key,
	Pass char(30) not null,
	NumNode smallint not null
);
create table Nodes (
	ID char(30) not null,
	NodeID char(50) PRIMARY KEY,
	NodeName varchar(50),
	CONSTRAINT FK_Nodes_Users FOREIGN KEY (ID) REFERENCES Users (ID)
);
create table CurrentData (
	ID char(30) not null,
	TimeGet datatime,
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
	IndexData int(11) not null,
	ID char(30) not null,
	TimeGet datatime,
	PV_Vol float,
	PV_Amp float,
	Bus float,
	Pac float,
	NodeID char(50) not null,
	CONSTRAINT PK_Collected PRIMARY KEY (ID, NodeID, IndexData),
	CONSTRAINT FK_Collected_Nodes FOREIGN KEY (NodeID) REFERENCES Nodes (NodeID),
	CONSTRAINT FK_Collected_Users FOREIGN KEY (ID) REFERENCES Users (ID)
);