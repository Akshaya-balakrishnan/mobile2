package com.capgemini.truckbooking.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.truckbooking.Exception.TTBException;
import com.capgemini.truckbooking.Utility.JdbcUtility;
import com.capgemini.truckbooking.bean.BookingBean;
import com.capgemini.truckbooking.bean.QueryMapper;
import com.capgemini.truckbooking.bean.TruckBean;
import com.capgemini.truckbooking.client.BookingClient;

public class TruckDao implements ITruckDao {
	PreparedStatement statement = null;
	Connection connection = null;
	boolean checkFlag=false;
	static Logger logger = Logger.getLogger(TruckDao.class);
	/***
	 * method		:getAllTruckDetails
	 * argument		:It's take model argument as an argument
	 * return type	:list
	 * Author		:Capgemini
	 * Date			:18-Jan-2018
	 */

	@Override
	public List<TruckBean> getAllTruckDetails() throws TTBException {
		logger.info("inside getAllTruckDetails");
		List<TruckBean> list = new ArrayList<>();
		ResultSet resultSet = null;
		connection = JdbcUtility.getConnection();
		try {
			statement = connection.prepareStatement(QueryMapper.getTruckDetails);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				logger.debug("inside while");
				int truckId = resultSet.getInt(1);
				String truckType = resultSet.getString(2);
				String origin = resultSet.getString(3);
				String destination = resultSet.getString(4);
				float charges = resultSet.getFloat(5);
				int availableNos = resultSet.getInt(6);
				TruckBean bean = new TruckBean();
				bean.setTruckID(truckId);
				bean.setTruckType(truckType);
				bean.setOrigin(origin);
				bean.setDestination(destination);
				bean.setCharges(charges);
				bean.setAvailableNos(availableNos);
				list.add(bean);
				logger.debug("while excecuted");
			}
		} catch (SQLException e) {
			logger.error("inside catch");
			throw new TTBException("SQL query not executed in dao layer for view truck details");
		}finally {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.error("inside catch");
				throw new TTBException("proble in closing the statement");
			}
			try {
				connection.close();
			} catch (Exception e2) {
				logger.error("inside catch");
				throw new TTBException("connection is not closed");
			}
		}

		return list;
	}
	/***
	 * method		:getvalidTruck
	 * argument		:It's take truckid as an argument
	 * return type	:Boolean
	 * Author		:Capgemini
	 * Date			:18-Jan-2018
	 */


	@Override
	public boolean getvalidTruck(int truckId) throws TTBException {
		List<TruckBean> list2 = new ArrayList<>();
		ResultSet resultSet = null;
		connection = JdbcUtility.getConnection();
		try {
			statement = connection.prepareStatement(QueryMapper.checkTruck);
			statement.setInt(1, truckId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) 
			{
				logger.debug("inside while");
				String truckType = resultSet.getString(1);
				TruckBean bean = new TruckBean();
				bean.setTruckType(truckType);
				list2.add(bean);
				logger.debug("while excecuted");
			}
			if (list2.isEmpty()) {
				checkFlag = false;
			} else {
				checkFlag = true;
			}
		} catch (SQLException e) {
			throw new TTBException("INVALID SQL STATEMENT IN GETVALIDTRUCK BLOCK");
		}finally {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.error("inside catch");
				throw new TTBException("proble in closing the statement");
			}
			try {
				connection.close();
			} catch (Exception e2) {
				logger.error("inside catch");
				throw new TTBException("connection is not closed");
			}
		}
	
		return checkFlag;
	}
	/***
	 * method		:getTruckQuantity
	 * argument		:It's take truckid as an argument
	 * return type	:Boolean
	 * Author		:Capgemini
	 * Date			:18-Jan-2018
	 */

	@Override
	public int getTruckQuantity(int truckId) throws TTBException {
		ResultSet resultSet = null;
		connection = JdbcUtility.getConnection();
		int quantity = 0;
		try {
			statement = connection.prepareStatement(QueryMapper.getQuantity);
			statement.setInt(1, truckId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) 
			{	logger.debug("inside while");
				quantity=resultSet.getInt(1);
				logger.debug("while excecuted");
			}
		} catch (SQLException e) {
			logger.error("inside catch");
			throw new TTBException("INVALID SQL STATEMENT IN GETTRUCKQUANTITY BLOCK");
		}finally {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.error("inside catch");
				throw new TTBException("proble in closing the statement");
			}
			try {
				connection.close();
			} catch (Exception e2) {
				logger.error("inside catch");
				throw new TTBException("connection is not closed");
			}
		}
		return quantity;
	}
	/***
	 * method		:getTruckQuantity
	 * argument		:It's take model argument as an argument
	 * return type	:BookingId
	 * Author		:Capgemini
	 * Date			:18-Jan-2018
	 */

	@Override
	public int insertBooking(BookingBean bookingBean) throws TTBException {
		ResultSet result =null;
		int bookingId=0;
		connection = JdbcUtility.getConnection();
		Date dateOFTransport = Date.valueOf(bookingBean.getDateOfTransport());
		try {
			statement = connection.prepareStatement(QueryMapper.insertBooking);
			statement.setString(1, bookingBean.getCustId());
			statement.setLong(2, bookingBean.getCustMobile());
			statement.setInt(3, bookingBean.getTruckId());
			statement.setInt(4, bookingBean.getNoOfTrucks());
			statement.setDate(5, dateOFTransport);
			statement.executeUpdate();
			statement = connection.prepareStatement(QueryMapper.setQuantity);
			statement.setInt(1, bookingBean.getNoOfTrucks());
			statement.setInt(2, bookingBean.getTruckId());
			statement.executeUpdate();			
			statement = connection.prepareStatement(QueryMapper.curval);
			result=statement.executeQuery();
		result.next();
		bookingId=result.getInt(1);
		
		} catch (SQLException e) {
			logger.error("inside catch");
			throw new TTBException("Query not executed in insertblock");
		}finally {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.error("inside catch");
				throw new TTBException("proble in closing the statement");
			}
			try {
				connection.close();
			} catch (Exception e2) {
				logger.error("inside catch");
				throw new TTBException("connection is not closed");
			}
		}
		return bookingId;
	}

}
