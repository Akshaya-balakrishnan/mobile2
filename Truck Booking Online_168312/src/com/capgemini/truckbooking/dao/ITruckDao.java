package com.capgemini.truckbooking.dao;

import java.util.List;

import com.capgemini.truckbooking.Exception.TTBException;
import com.capgemini.truckbooking.bean.BookingBean;
import com.capgemini.truckbooking.bean.TruckBean;

public interface ITruckDao {

	List<TruckBean> getAllTruckDetails() throws TTBException;

	boolean getvalidTruck(int truckId) throws TTBException;

	int getTruckQuantity(int truckId) throws TTBException;

	int insertBooking(BookingBean bookingBean) throws TTBException;

}
