package com.capgemini.truckbooking.service;

import java.util.List;

import com.capgemini.truckbooking.Exception.TTBException;
import com.capgemini.truckbooking.bean.BookingBean;
import com.capgemini.truckbooking.bean.TruckBean;

public interface ITruckService {

	boolean getvalidation(String custId);

	List<TruckBean> getAllTruckDetails() throws TTBException;

	boolean getvalidTruck(int truckId) throws TTBException;

	int gettruckQuantity(int truckId) throws TTBException;

	boolean getvaldatenumber(long custMobile);

	int insertBooking(BookingBean bookingBean) throws TTBException;



}
