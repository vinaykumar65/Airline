package com.airline.reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DomesticFlightDAO {

    public List<Flight> getDomesticFlights() throws SQLException {
        List<Flight> flights = new ArrayList<>();
        String query = "SELECT * FROM domestic_flights";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Flight flight = new Flight(
                    resultSet.getInt("flight_id"),
                    resultSet.getString("source_city"),
                    resultSet.getString("destination_city"),
                    resultSet.getTimestamp("departure_time"),
                    resultSet.getTimestamp("arrival_time"),
                    resultSet.getBigDecimal("economy_fare"),
                    resultSet.getBigDecimal("business_fare"),
                    resultSet.getInt("seats_available")
                );
                flights.add(flight);
            }
        }
        return flights;
    }

    public List<Flight> searchDomesticFlights(String sourceCity, String destinationCity) throws SQLException {
        List<Flight> flights = new ArrayList<>();
        String query = "SELECT * FROM domestic_flights WHERE source_city = ? AND destination_city = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, sourceCity);
            statement.setString(2, destinationCity);
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Flight flight = new Flight(
                        resultSet.getInt("flight_id"),
                        resultSet.getString("source_city"),
                        resultSet.getString("destination_city"),
                        resultSet.getTimestamp("departure_time"),
                        resultSet.getTimestamp("arrival_time"),
                        resultSet.getBigDecimal("economy_fare"),
                        resultSet.getBigDecimal("business_fare"),
                        resultSet.getInt("seats_available")
                    );
                    flights.add(flight);
                }
            }
        }
        return flights;
    }
}