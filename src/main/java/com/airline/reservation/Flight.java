package com.airline.reservation;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Flight {
    private int flightId;
    private String sourceCity;
    private String destinationCity;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private BigDecimal economyFare;
    private BigDecimal businessFare;
    private int seatsAvailable;

    public Flight(int flightId, String sourceCity, String destinationCity, Timestamp departureTime, Timestamp arrivalTime, BigDecimal economyFare, BigDecimal businessFare, int seatsAvailable) {
        this.setFlightId(flightId);
        this.setSourceCity(sourceCity);
        this.setDestinationCity(destinationCity);
        this.setDepartureTime(departureTime);
        this.setArrivalTime(arrivalTime);
        this.setEconomyFare(economyFare);
        this.setBusinessFare(businessFare);
        this.setSeatsAvailable(seatsAvailable);
    }

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public String getSourceCity() {
		return sourceCity;
	}

	public void setSourceCity(String sourceCity) {
		this.sourceCity = sourceCity;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public Timestamp getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Timestamp departureTime) {
		this.departureTime = departureTime;
	}

	public Timestamp getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public BigDecimal getEconomyFare() {
		return economyFare;
	}

	public void setEconomyFare(BigDecimal economyFare) {
		this.economyFare = economyFare;
	}

	public BigDecimal getBusinessFare() {
		return businessFare;
	}

	public void setBusinessFare(BigDecimal businessFare) {
		this.businessFare = businessFare;
	}

	public int getSeatsAvailable() {
		return seatsAvailable;
	}

	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}
}
