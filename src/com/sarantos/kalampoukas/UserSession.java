package com.sarantos.kalampoukas;

import java.util.Date;

public final class UserSession {

    private static UserSession instance;

    // User info
    private int user_id;
    private String email;
    private String name;
    private String surname;
    
    // Booking info
    private Date check_in;
    private Date check_out;
    private int persons;
    private double total_price;

    private UserSession(int user_id, String email, String name, String surname) {
        this.user_id = user_id;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public static UserSession getInstance(int user_id, String email, String name, String surname) {
        if(instance == null) {
            instance = new UserSession(user_id, email, name, surname);
        }
        return instance;
    }
    
    public static UserSession getInstance() {
    	return instance;
    }

    public int getUserId() {
        return user_id;
    }
    
    public String getEmail() {
    	return email;
    }

    public String getName() {
    	return name;
    }
    
    public String getSurname() {
    	return surname;
    }

    public void cleanUserSession() {
        user_id = 0;
        email = "";
        name = "";
        surname = "";
    }
    
    public Date getCheckIn() {
    	return check_in;
    }
    
    public Date getCheckOut() {
    	return check_out;
    }
    
    public int getPersons() {
    	return persons;
    }
    
    public double getTotalPrice() {
    	return total_price;
    }
    
    public void setBookingInfo(Date check_in, Date check_out, int persons, double total_price) {
    	setCheckIn(check_in);
    	setCheckOut(check_out);
    	setPersons(persons);
    	setTotalPrice(total_price);
    }
    
    public void setCheckIn(Date check_in) {
    	this.check_in = check_in;
    }
    
    public void setCheckOut(Date check_out) {
    	this.check_out = check_out;
    }
    
    public void setPersons(int persons) {
    	this.persons = persons;
    }

    public void setTotalPrice(double total_price) {
    	this.total_price = total_price;
    }	
    
    @Override
    public String toString() {
        return "UserSession{" +
                "email='" + email + '}';
    }
}