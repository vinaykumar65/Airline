package com.airline.reservation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BookFlight")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the request
        String passengerName = request.getParameter("passengerName");
        int flightId;
        String seatClass = request.getParameter("seatClass");
        int numPassengers;
        String journeyDate = request.getParameter("journeyDate"); // Retrieve the journey date

        // Validate and parse parameters
        try {
            flightId = Integer.parseInt(request.getParameter("flightId"));
            numPassengers = Integer.parseInt(request.getParameter("numPassengers"));
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid input for flightId or numPassengers. Please check your input.");
            return;
        }

        // SQL query to insert booking
        String query = "INSERT INTO bookings (flight_id, passenger_name, seat_class, num_passengers, booking_date) VALUES (?, ?, ?, ?, ?)";

        // Connect to the database and execute the query
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set parameters for the query
            statement.setInt(1, flightId);
            statement.setString(2, passengerName);
            statement.setString(3, seatClass);
            statement.setInt(4, numPassengers);

            // Convert the date string to a SQL date format
            java.sql.Date sqlDate = java.sql.Date.valueOf(journeyDate);
            statement.setDate(5, sqlDate);

            // Execute the update
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                response.getWriter().println("Booking successful!");
            } else {
                response.getWriter().println("No rows affected. Booking might have failed.");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace to understand the error
            response.getWriter().println("Booking failed! Please try again.");
        }
    }
}