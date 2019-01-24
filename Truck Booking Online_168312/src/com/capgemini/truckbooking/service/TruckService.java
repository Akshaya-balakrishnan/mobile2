package com.capgemini.truckbooking.service;

import java.util.List;
import java.util.regex.Pattern;

import com.capgemini.truckbooking.Exception.TTBException;
import com.capgemini.truckbooking.bean.BookingBean;
import com.capgemini.truckbooking.bean.TruckBean;
import com.capgemini.truckbooking.dao.ITruckDao;
import com.capgemini.truckbooking.dao.TruckDao;

public class TruckService implements ITruckService {
ITruckDao truckDao=new TruckDao();
	@Override
	public boolean getvalidation(String custId) {
		String valid = "[A-Z]{1}[0-9]{6}$";
		boolean input = Pattern.matches(valid, custId);
		return input;
	}

	@Override
	public List<TruckBean> getAllTruckDetails() throws TTBException {
		// TODO Auto-generated method stub
		return truckDao.getAllTruckDetails();
	}

	@Override
	public boolean getvalidTruck(int truckId) throws TTBException {
		// TODO Auto-generated method stub
		return truckDao.getvalidTruck(truckId);
	}

	@Override
	public int gettruckQuantity(int truckId) throws TTBException {
		// TODO Auto-generated method stub
		return truckDao.getTruckQuantity(truckId);
	}

	@Override
	public boolean getvaldatenumber(long custMobile) {
		String number=String.valueOf(custMobile);
		String val = "[6|7|8|9]{1}[0-9]{9}";
		boolean input=Pattern.matches(val, number);
		return input;
	}

	@Override
	public int insertBooking(BookingBean bookingBean) throws TTBException {
		// TODO Auto-generated method stub
		return truckDao.insertBooking(bookingBean);
	}

}
