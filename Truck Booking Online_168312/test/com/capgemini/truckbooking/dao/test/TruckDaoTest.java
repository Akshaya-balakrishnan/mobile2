package com.capgemini.truckbooking.dao.test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.capgemini.truckbooking.Exception.TTBException;
import com.capgemini.truckbooking.bean.BookingBean;
import com.capgemini.truckbooking.dao.ITruckDao;
import com.capgemini.truckbooking.dao.TruckDao;


public class TruckDaoTest {
	ITruckDao truckDao=null;
	@Before
	public void setUp() throws Exception {
		 truckDao=new TruckDao();
	}

	@After
	public void tearDown() throws Exception {
	truckDao=null;
	}

	@Test
	public void testInsertBooking() {
		String jd = "22-01-2019";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate date_dob = LocalDate.parse(jd, formatter);
		BookingBean bookingBean=new BookingBean();
		bookingBean.setCustId("A111111");
		bookingBean.setTruckId(1000);
		bookingBean.setNoOfTrucks(1);
		bookingBean.setCustMobile(9012345678l);
		bookingBean.setDateOfTransport(date_dob);
		try {
			int result = truckDao.insertBooking(bookingBean);
			assertEquals(1003, result);

		} catch (TTBException e) {
			
			e.printStackTrace();
		}
	}

}
