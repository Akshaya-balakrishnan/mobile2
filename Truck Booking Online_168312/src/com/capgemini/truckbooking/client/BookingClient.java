package com.capgemini.truckbooking.client;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.truckbooking.Exception.TTBException;
import com.capgemini.truckbooking.bean.BookingBean;
import com.capgemini.truckbooking.bean.TruckBean;
import com.capgemini.truckbooking.service.ITruckService;
import com.capgemini.truckbooking.service.TruckService;

public class BookingClient {
	static Logger logger = Logger.getLogger(BookingClient.class);

	public static void main(String[] args) throws TTBException {
		PropertyConfigurator.configure("resource/log4j.properties");
		boolean menuFlag = false;
		boolean doFlag = false;
		DateTimeFormatter formatter = null;
		LocalDate dateOfTransport = null;
		do {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Transpot Truck Booking Online");
			System.out.println("1. Book Trucks");
			System.out.println("2. Exit");
			System.out.println("Select your choices : ");
			int choices;
			try {
				choices = scanner.nextInt();
				if (choices > 0 && choices < 3) {
					menuFlag = true;
					switch (choices) {

					case 1:

						System.out.println("Welcome to booking portal");
						String custId;
						ITruckService service = new TruckService();
						do {
							scanner = new Scanner(System.in);
							System.out.println("Enter Your CustId");
							custId = scanner.nextLine();
							boolean validflag = service.getvalidation(custId);
							if (validflag == true)
								doFlag = validflag;
							else
								System.err.println("CustId should be one Alphabet(uppercase) followed by 6 digits");

						} while (!doFlag);
						try {
							List<TruckBean> list = service.getAllTruckDetails();
							if (list.size() > 0) {
								System.out.println("TRUCKID" + "        " + "TRUCKTYPE" + "           " + "ORIGIN"
										+ "        " + "DESTINATION" + "        " + "CHARGER" + "        "
										+ "AVAILABLE NOS");
								for (TruckBean truckBean : list) {
									System.out.println(truckBean.getTruckID() + "        " + truckBean.getTruckType()
											+ "           " + truckBean.getOrigin() + "        "
											+ truckBean.getDestination() + "        " + truckBean.getCharges()
											+ "        " + truckBean.getAvailableNos());

								}
							}
						} catch (Exception e) {
							throw new TTBException("no date found");
						}
						doFlag = false;
						int truckId = 0;
						do {
							scanner = new Scanner(System.in);
							System.out.println("ENTER TRUCKID ");
							try {
								truckId = scanner.nextInt();
								boolean validflag = service.getvalidTruck(truckId);
								if (validflag == true)
									doFlag = validflag;
							} catch (InputMismatchException e) {
								System.err.println("ENTER VALID TRUCKID");
								doFlag = false;
							}

						} while (!doFlag);

						int truckQuantity = 0;
						doFlag = false;
						do {
							scanner = new Scanner(System.in);
							System.out.println("Enter number of truck needed");
							try {
								truckQuantity = scanner.nextInt();
								int value = service.gettruckQuantity(truckId);
								if (truckQuantity <= value) {
									doFlag = true;
								} else {
									System.out.println("enter quantity above that we have");
								}
							} catch (InputMismatchException e) {
								System.err.println("ONLY INTEGER");
								doFlag = false;
							}

						} while (!doFlag);
						long custMobile = 0;
						doFlag = false;
						do {
							scanner = new Scanner(System.in);
							System.out.println("Enter Your phone");
							try {
								custMobile = scanner.nextLong();
								boolean valflag = service.getvaldatenumber(custMobile);
								if (valflag == true)
									doFlag = valflag;
								else
									System.err.println("mobile number should be 10 digits");
							} catch (InputMismatchException e) {
								System.err.println("only integer value");
								doFlag = false;
							}
						} while (!doFlag);
						String date;
						doFlag = false;
						do {
							scanner = new Scanner(System.in);
							System.out.println("date of booking (yyyy-MM-dd)");
							date = scanner.nextLine();

							formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

							try {
								LocalDate start = LocalDate.now();

								dateOfTransport = LocalDate.parse(date, formatter);

								Period period = start.until(dateOfTransport);
								int d = period.getDays();
								if (d >= 1) 
								{
									doFlag = true;
								}else if(d<0){
								System.out.println("you r trying to book for past day");	
								}
								else {
									System.err.println("you have to book 24 hrs prior");
								}
							} catch (DateTimeParseException e) {
								doFlag = false;
								System.err.println(
										"date is not in the given format, give the date in dd-MM-yyyy format ");
							}
						} while (!doFlag);
						BookingBean bookingBean = new BookingBean();
						bookingBean.setCustId(custId);
						bookingBean.setTruckId(truckId);
						bookingBean.setNoOfTrucks(truckQuantity);
						bookingBean.setCustMobile(custMobile);
						bookingBean.setDateOfTransport(dateOfTransport);
						try {
							int bookingId = service.insertBooking(bookingBean);
							System.out.println("YOUR BOOKING ID:" + bookingId);
						} catch (Exception e) {
							System.err.println("insert service is not established");
						}
						break;
					case 2:
						System.exit(0);
						break;
					default:
						System.out.println("Invalid Selection Try Again");
						System.out.println();
						menuFlag = false;
						break;
					}
				} else {
					System.err.println("PLEASE ENTER VALID OPTION RANGE(1-3)");
					menuFlag = false;
					System.out.println("Select your choices : ");
				}
			} catch (InputMismatchException e) {
				System.err.println("INPUT MUST BE INTEGER");
				System.out.println();
			}
		} while (!menuFlag);

	}

}
