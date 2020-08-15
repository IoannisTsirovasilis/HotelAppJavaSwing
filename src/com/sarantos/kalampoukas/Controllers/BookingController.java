package com.sarantos.kalampoukas.Controllers;

import java.sql.SQLException;
import java.util.List;

import com.sarantos.kalampoukas.Models.Booking;
import com.sarantos.kalampoukas.Models.DbContext;

public class BookingController {
public BookingController() {}
	
	public List<Booking> getBookings() throws Exception {
		DbContext context;
		try {
			context = new DbContext();
			List<Booking> bookings = context.getBookings();
			return bookings;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.toString());
			e.printStackTrace();
			throw new Exception();
		}
	}
	
	public boolean cancelBooking(int id) throws Exception {
		DbContext context;
		try {
			context = new DbContext();
			boolean succeeded = context.cancelBooking(id);
			return succeeded;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		throw new Exception();
	}
}
