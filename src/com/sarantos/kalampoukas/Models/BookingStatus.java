package com.sarantos.kalampoukas.Models;

public class BookingStatus implements IModel {
	private int id;
	private String name;
	
	public BookingStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
