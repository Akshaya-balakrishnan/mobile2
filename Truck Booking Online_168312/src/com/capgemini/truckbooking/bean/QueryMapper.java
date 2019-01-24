package com.capgemini.truckbooking.bean;

public interface QueryMapper {
public static final String getTruckDetails="SELECT * FROM TruckDetails";
public static final String checkTruck="SElECT trucktype FROM TruckDetails WHERE TruckID=?";
public static final String getQuantity="SELECT availableNos FROM TruckDetails WHERE TruckID=?";
public static final String insertBooking="INSERT INTO bookingdetails VALUES(booking_id_seq.nextval,?,?,?,?,?)";
public static final String setQuantity="UPDATE truckDetails set availableNos=availableNos-? where TruckID=? ";
public static final String curval="select booking_id_seq.currval from dual";
}
