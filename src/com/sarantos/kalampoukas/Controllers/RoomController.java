package com.sarantos.kalampoukas.Controllers;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Models.DbContext;
import com.sarantos.kalampoukas.Models.Room;

public class RoomController {
	
	public RoomController() {}
	
	public List<Room> getRooms(Date from, Date to, int capacity) throws ClassNotFoundException, SQLException {
		DbContext context = new DbContext();
		List<Room> rooms = context.getRoomsInDateRangeAndCapacity(from, to, capacity);
		UserSession.getInstance().setCheckIn(from);
		UserSession.getInstance().setCheckOut(to);
		UserSession.getInstance().setPersons(capacity);
		return rooms;
	}
	
	public long book(int id, int capacity, double total_price) throws ClassNotFoundException, SQLException {
		DbContext context = new DbContext();
		int user_id = UserSession.getInstance().getUserId();
		int persons = UserSession.getInstance().getPersons();
		Date check_in = UserSession.getInstance().getCheckIn();
		Date check_out = UserSession.getInstance().getCheckOut();
		
		long bookingId = context.bookRoom(id, user_id, persons, total_price, check_in, check_out);
		UserSession.getInstance().setBookingId(bookingId);
		System.out.println("Booking Id = " + UserSession.getInstance().getBookingId());
		return UserSession.getInstance().getBookingId();
	}
}
