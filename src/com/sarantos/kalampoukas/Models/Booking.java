package com.sarantos.kalampoukas.Models;

import java.util.Date;

public class Booking {
	private int id;
	private int user_id;
	private int persons;
	private double total_price;
	private Date check_in;
	private Date check_out;
	private int status_id;
	private int room_id;
	private String email;
	
	public Booking(int id, int user_id, int persons,
			double total_price, Date check_in, Date check_out,
			int status_id, int room_id, String email) {
		this.id = id;
		this.user_id = user_id;
		this.persons = persons;
		this.total_price = total_price;
		this.check_in = check_in;
		this.check_out = check_out;
		this.status_id = status_id;
		this.room_id = room_id;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}
	
	public int getUserId() {
		return user_id;
	}
	
	public int getPersons() {
		return persons;
	}
	
	public double getTotalPrice() {
		return total_price;
	}
	
	public Date getCheckIn() {
		return check_in;
	}
	
	public Date getCheckOut() {
		return check_out;
	}
	
	public int getStatusId() {
		return status_id;
	}
	
	public int getRoomId() {
		return room_id;
	}
	
	public String getEmail() {
		return email;
	}
}
