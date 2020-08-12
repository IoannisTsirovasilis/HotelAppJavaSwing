package com.sarantos.kalampoukas.Controllers;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.JList;

import com.sarantos.kalampoukas.Models.DbContext;
import com.sarantos.kalampoukas.Models.Room;

public class RoomController {
	
	public RoomController() {}
	
	public static JList getRooms(Date from, Date to, int capacity) throws ClassNotFoundException, SQLException {
		DbContext context = new DbContext();
		List<Room> rooms = context.getRoomsInDateRangeAndCapacity(from, to, capacity);
		return new JList(rooms.toArray());
	}
}
