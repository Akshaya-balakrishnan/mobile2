CREATE TABLE TruckDetails(truckId number primary key, truckType varchar2(20), 
origin varchar2(20), destination varchar2(20), charges decimal, availableNos number);

INSERT INTO TruckDetails values(1000, '20 ton Trailer','Mumbai','Delhi', 30000, 3);
INSERT INTO TruckDetails values(2000, '20 ton Trailer','Delhi','Mumbai', 30000, 2);
INSERT INTO TruckDetails values(1001, '10 ton', 'Mumbai','Kolkota', 20000, 7);
INSERT INTO TruckDetails values(2001, '10 ton', 'Kolkota','Mumbai', 20000, 2);
INSERT INTO TruckDetails values(1002, '10 ton', 'Mumbai','Chennai', 18000, 7);
INSERT INTO TruckDetails values(1003, 'Mini truck', 'Mumbai','Pune',5000, 10);
INSERT INTO TruckDetails values(1004, 'Mini truck', 'Mumbai','Ahmedabad',7000, 10);
INSERT INTO TruckDetails values(3005, 'Tempo','Pune','Nasik', 4000, 6);

CREATE TABLE BookingDetails( bookingId number primary key, custId varchar2(20), 
custMobile number,  truckId REFERENCES TruckDetails(truckId), noOfTrucks number,
dateOfTransport date);

sequence:
========
CREATE SEQUENCE booking_id_seq Start with 1000;
