create database lab4;
use lab4;
#drop database lab4;

create table Trip(TripNumber int primary key auto_increment, 
					StartLocationName varchar(25),
                    DestinationName varchar(25)
                    );
                    
create table Bus(BusId int primary key auto_increment,
					Model varchar(25),
                    Year int
                    );
				
create table Driver(DriverName varchar(25) primary key,
					DriverPhoneNumb varchar(25)
                    );
            
create table TripOffering(TripNumber int auto_increment,
							Date date,
                            ScheduledStartTime time,
                            ScheduledArrivalTime time,
                            DriverName varchar(25),
                            BusId int,
                            
								foreign key (TripNumber) references Trip(TripNumber),
                                foreign key (BusId) references Bus(BusId)
                                on delete set null,
                                foreign key (DriverName) references Driver(DriverName),
                                primary key (TripNumber, Date, ScheduledStartTime)
                            );
                            
                    
create table Stop(StopNumber int primary key auto_increment,
					StopAddress varchar(50)
                    );
				
create table ActualTripStopInfo(TripNumber int,
								Date date,
								ScheduledStartTime time,
								StopNumber int,
								ScheduledArrivalTime time,
								ActualStartTime time,
								ActualArrivalTime time,
								NumbOfPassengersIn int,
								NumbOfPassengersOut int,
                                
									foreign key(TripNumber, Date, ScheduledStartTime) references TripOffering(TripNumber, Date, ScheduledStartTime)
                                    on delete cascade,
                                    foreign key(StopNumber) references Stop(StopNumber),
                                    primary key(TripNumber, Date, ScheduledStartTime, StopNumber)
                                );
		
                                
create table TripStopInfo(TripNumber int,
							StopNumber int,
							SequenceNumber int,
                            DrivingTime time,
                            
								foreign key (TripNumber) references Trip(TripNumber),
                                foreign key (StopNumber) references Stop(StopNumber),
                                primary key(TripNumber, StopNumber)
                            );

#---------------------------------------------------------------------------------------------------------------------------------
					
					
#----------------------------------------------------------------------------------------------------------------------------------
select * from Trip;
select * from TripOffering;
select * from Bus;
select * from Driver;
select * from Stop;
select * from ActualTripStopInfo;
select * from TripStopInfo;

#drop table Trip;
#drop table TripOffering;
#drop table Bus;
#drop table Driver;
#drop table Stop;
#drop table ActualTripStopInfo;
#drop table TripStopInfo;
#---------------------------------------------------------------------------------------------------------------------------------
insert into Trip values(1, "Upland", "Rancho Cucamonga");
insert into Trip values(2, "Upland", "Ontario");
insert into Trip values(3, "Pomona", "Ontario");

insert into Bus values(1, "Ford", 2000);
insert into Bus values(2, "Ford", 2002);
insert into Bus values(3, "Volvo", 1995);

insert into Driver values("James C", "9092334455");
insert into Driver values("Oscar M", "9352324335");
insert into Driver values("Jackie A", "1234567890");

insert into TripOffering values(1, "2023-11-16", "17:00:00", "18:00:00", "James C", 1);
insert into TripOffering values(2, "2023-11-16", "16:00:00", "17:00:00", "Oscar M", 2);
insert into TripOffering values(3, "2023-11-01", "12:00:00", "14:00:00", "Jackie A", 3);

insert into Stop values(1, "RanchoMall");
insert into Stop values(2, "Ontario Mall");
insert into Stop values(3, "Cal Poly Pomona");
insert into Stop values(4, "Downtown Upland");
insert into Stop values(5, "Downtown Rancho Cucamonga");

insert into ActualTripStopInfo values(1, "2023-11-16", "17:00:00", 1, "18:00:00", "17:00:00", "18:00:00", 10, 10);
insert into ActualTripStopInfo values(2, "2023-11-16", "16:00:00", 2, "17:00:00", "16:05:00", "17:02:00", 15, 15);
insert into ActualTripStopInfo values(3, "2023-11-01", "12:00:00", 2, "14:00:00", "12:04:00", "14:08:00", 4, 3);
insert into ActualTripStopInfo values(1, "2023-11-16", "17:00:00", 4, "18:00:00", "17:04:00", "18:08:00", 2, 5);

insert into TripStopInfo values(1, 1, 1, "18:00:00");
insert into TripStopInfo values(2, 2, 1, "17:00:00");
insert into TripStopInfo values(1, 4, 2, "17:30:00");
insert into TripStopInfo values(3, 2, 2, "14:00:00");

#----------------------------------------------------------------------------------------------------------------------------------
#1. Display the schedule of all trips for a given StartLocationName and Destination Name,
#and Date. In addition to these attributes, the schedule includes: Scheduled StartTime,
#ScheduledArrivalTime , DriverID, and BusID.
select * 
from Trip t
inner join TripOffering o on t.TripNumber = o.TripNumber
where t.StartLocationName = "Upland" and t.DestinationName = "Rancho Cucamonga";



#2 delete a trip offering (add contraints to the table creations to account for child's data)
#DELETE FROM TripOffering WHERE TripNumber=1 AND date="2023-11-16" and ScheduledStartTime="17:00:00";
													


#3 add a set of trip offering 
#insert into TripOffering values(1, "2023-11-16", "17:00:00", "18:00:00", "James C", 1);


#4 display the weekly schedule of given driver and date 
select * 
from Driver d
inner join TripOffering t on d.DriverName = t.DriverName
where d.DriverName = "James C"
and t.date between '2023-11-10' and DATE_ADD('2023-11-17', interval 7 day);
                                