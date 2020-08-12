package com.sarantos.kalampoukas.Models;

public class RoomStatus implements IModel {
	private int id;
	private String name;
	
	public RoomStatus(int id, String name) {
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
