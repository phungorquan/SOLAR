SELECT NOW();
use ceec_solar;
drop table sessions;
select * from sessions;
delete from sessions;
create table CollectedData (
	IndexData int(16) auto_increment,
	ID char(30) not null,
	TimeGet datetime,
	PV_Vol float,
	PV_Amp float,
	Bus float,
	Pac float,
	NodeID char(50) not null,
    primary key(IndexData),
	CONSTRAINT FK_Collected_Nodes FOREIGN KEY (NodeID) REFERENCES Nodes (NodeID),
	CONSTRAINT FK_Collected_Users FOREIGN KEY (ID) REFERENCES Users (ID)
);

select * from currentdata;

alter table collecteddata drop COLUMN PV_Vol, drop COLUMN PV_Amp, drop COLUMN Bus;

ALTER TABLE users ADD Company varchar(50), add Email varchar(50), add FirstName varchar(30), add LastName varchar(30), add Address varchar(50), add City varchar(30), add About varchar(500), add CoverAvatar varchar(200), add Avatar varchar(200), add LinkInfo varchar(100)
, add Slogan varchar(500);

ALTER TABLE users ADD Country varchar(50);

ALTER TABLE users drop column Avatar, drop column CoverAvatar;
ALTER TABLE users ADD Avatar varchar(500), add CoverAvatar varchar(500);

update users set Company='UIT', Email='wathui99@gmail.com',
FirstName='Lee', LastName='Thuc', Address='Linh Trung - Thu Duc',
City='HCM', Country='Vietnam', About='Single is better than every f*** thing in the world',
Slogan='Everyday is not sunday';

update users set Avatar='https://scontent.fsgn4-1.fna.fbcdn.net/v/t1.0-9/31562203_2034794980067323_2424238627451043840_n.jpg?_nc_cat=105&_nc_oc=AQkSCViLwTCFN9LL-idGVdDlMPYwkWcpbq4aJcvPwPU557Y_f6QcshGZfLFWvLLHlk4&_nc_ht=scontent.fsgn4-1.fna&oh=39ad1c72bb00a4160bf08d18c0f83fc1&oe=5CDF2160',
CoverAvatar='https://st.quantrimang.com/photos/image/2018/01/10/hinh-nen-4k-4.jpg',
LinkInfo='thuc.lee.944';

select * from users;

select * from collecteddata;

insert into CollectedData (ID,TimeGet,PV_Vol, PV_Amp, Bus, Pac, NodeID) values ('CEEC', '2019-03-05 00:00:00', 123, 234, 123, 123, 'CEEC_0');
SELECT DISTINCT YEAR(TimeGet) as yearTime, MONTH(TimeGet) as monthTime, DAY(TimeGet) as dayTime FROM CollectedData;
select * from collectedData order by TimeGet ASC;
delete from collectedData;

select TimeGet, AVG(Pac) as Pac from collecteddata where NodeID=upper('ceec_0') and year(TimeGet)= 2019 and month(TimeGet)= 3 group by day(TimeGet) order by TimeGet ASC;
select * from collectedData;
SET SQL_SAFE_UPDATES = 0;
delete from currentdatacollecteddatausers;
alter table users drop NumNode;

insert into Users (ID, Pass, NumNode) values (upper('ceec'), AES_ENCRYPT('ce.uit.edu.vn', 'qwerty123456'), 0);
insert into nodes (ID, NodeID, NodeName) values(upper('ceec'), upper('ceec_0'), 'Toa E-UIT');
insert into nodes (ID, NodeID, NodeName) values(upper('ceec'), upper('ceec_1'), 'Toa B-UIT');

insert into currentdata (ID, TimeGet, PV_Vol, PV_Amp, Bus, AC_Vol, AC_Hz, Tem, Pac, EToday, EAll, StatusConnect, NodeID) values
(upper('ceec'), '2019-3-13 17:34:13', 240.4, 4.5, 130, 230.4, 50.1, 30, 12312, 234, 1234.5, 1, upper('ceec_1'));

insert into collecteddata (ID, NodeID, TimeGet, Pac) values (upper('ceec'), upper('ceec_1'), '2019-3-12 14:59:40', 113);

select * from currentdata;

delete from currentdata;