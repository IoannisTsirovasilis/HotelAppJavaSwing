package com.sarantos.kalampoukas.Models;

public class Room implements IModel {
	private int id;
	private int capacity;
	private double price;
	private String description;
	private String image_encoded;
	private int status_id;
	
	public Room(int id, int capacity, double price, String description,
			String image_encoded, int status_id) {
		this.id = id;
		this.capacity = capacity;
		this.price = price;
		this.description = description;
		this.image_encoded = image_encoded;
		this.status_id = status_id;
	}
	
	public int getId() {
		return id;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public double getPrice() {
		return price;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getImageEncoded() {
		return image_encoded;
	}
	
	public int getStatusId() {
		return status_id;
	}
}
