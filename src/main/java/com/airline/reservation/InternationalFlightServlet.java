package com.airline.reservation;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InternationalFlights")
public class InternationalFlightServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get search parameters if available
        String sourceCity = request.getParameter("sourceCity");
        String destinationCity = request.getParameter("destinationCity");

        try {
            InternationalFlightDAO dao = new InternationalFlightDAO();
            List<Flight> flights;
            if (sourceCity != null && destinationCity != null) {
                flights = dao.searchInternationalFlights(sourceCity, destinationCity);
            } else {
                flights = dao.getInternationalFlights();
            }
            
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            out.println("<html><head>");
            out.println("<link rel='stylesheet' type='text/css' href='css/styles.css'>");
            out.println("</head><body>");
            out.println("<h1>International Flights</h1>");

            // Search Form
            out.println("<form action='InternationalFlights' method='get'>");
            out.println("<label for='sourceCity'>Source City:</label>");
            out.println("<input type='text' id='sourceCity' name='sourceCity'><br>");
            out.println("<label for='destinationCity'>Destination City:</label>");
            out.println("<input type='text' id='destinationCity' name='destinationCity'><br>");
            out.println("<input type='submit' value='Search Flights'>");
            out.println("</form>");
            out.print("<br>");
            out.print("<br>");
            out.print("<br>");
            out.print("<br>");
            
            // Display Flights Table
            out.println("<table border='1'><tr><th>Flight ID</th><th>Source City</th><th>Destination City</th><th>Departure Time</th><th>Arrival Time</th><th>Economy Fare</th><th>Business Fare</th><th>Seats Available</th></tr>");
            for (Flight flight : flights) {
                out.println("<tr><td>" + flight.getFlightId() + "</td><td>" + flight.getSourceCity() + "</td><td>" + flight.getDestinationCity() + "</td><td>" + timeFormat.format(flight.getDepartureTime()) + "</td><td>" + timeFormat.format(flight.getArrivalTime()) + "</td><td>" + flight.getEconomyFare() + "</td><td>" + flight.getBusinessFare() + "</td><td>" + flight.getSeatsAvailable() + "</td></tr>");
            }
            out.println("</table>");
            
            // Add Booking Form
            out.println("<h2>Book a Flight</h2>");
            out.println("<form action='InternationalFlights' method='post'>");
            out.println("<label for='passengerName'>Passenger Name:</label>");
            out.println("<input type='text' id='passengerName' name='passengerName' required><br>");
            out.println("<label for='flightId'>Flight ID:</label>");
            out.println("<input type='text' id='flightId' name='flightId' required><br>");
            out.println("<label for='seatClass'>Seat Class:</label>");
            out.println("<select id='seatClass' name='seatClass' required>");
            out.println("<option value='Economy'>Economy</option>");
            out.println("<option value='Business'>Business</option>");
            out.println("</select><br>");
            out.println("<label for='numPassengers'>Number of Passengers:</label>");
            out.println("<input type='number' id='numPassengers' name='numPassengers' min='1' max='9' required><br>");
            out.println("<label for='journeyDate'>Date of Journey:</label>");
            out.println("<input type='date' id='journeyDate' name='journeyDate' required><br>");
            out.println("<input type='submit' value='Book Flight'>");
            out.println("</form>");
            
            out.println("</body></html>");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String passengerName = request.getParameter("passengerName");
        int flightId = Integer.parseInt(request.getParameter("flightId"));
        String seatClass = request.getParameter("seatClass");
        int numPassengers = Integer.parseInt(request.getParameter("numPassengers"));
        String journeyDate = request.getParameter("journeyDate");

        // Initialize variables to store flight details
        String sourceCity = "";
        String destinationCity = "";
        String departureTime = "";
        String arrivalTime = "";

        // Get fare details and calculate total amount
        BigDecimal totalAmount = BigDecimal.ZERO;
        Connection connection = null;
        PreparedStatement flightStatement = null;
        PreparedStatement bookingStatement = null;
        PreparedStatement updateSeatsStatement = null;
        ResultSet flightResultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Fetch flight details
            String flightQuery = "SELECT source_city, destination_city, departure_time, arrival_time, economy_fare, business_fare, seats_available FROM international_flights WHERE flight_id = ?";
            flightStatement = connection.prepareStatement(flightQuery);
            flightStatement.setInt(1, flightId);
            flightResultSet = flightStatement.executeQuery();

            if (flightResultSet.next()) {
                sourceCity = flightResultSet.getString("source_city");
                destinationCity = flightResultSet.getString("destination_city");
                departureTime = flightResultSet.getString("departure_time");
                arrivalTime = flightResultSet.getString("arrival_time");
                BigDecimal economyFare = flightResultSet.getBigDecimal("economy_fare");
                BigDecimal businessFare = flightResultSet.getBigDecimal("business_fare");
                int seatsAvailable = flightResultSet.getInt("seats_available");

                if ("Economy".equalsIgnoreCase(seatClass)) {
                    totalAmount = economyFare.multiply(BigDecimal.valueOf(numPassengers));
                } else if ("Business".equalsIgnoreCase(seatClass)) {
                    totalAmount = businessFare.multiply(BigDecimal.valueOf(numPassengers));
                } else {
                    sendSweetAlert(response, "Error", "Invalid seat class. Please choose Economy or Business.", "error");
                    return;
                }

                // Check seat availability
                if (seatsAvailable < numPassengers) {
                    sendSweetAlert(response, "Error", "Not enough seats available.", "error");
                    connection.rollback();
                    return;
                }

                // Update seats available
                String updateSeatsQuery = "UPDATE international_flights SET seats_available = seats_available - ? WHERE flight_id = ?";
                updateSeatsStatement = connection.prepareStatement(updateSeatsQuery);
                updateSeatsStatement.setInt(1, numPassengers);
                updateSeatsStatement.setInt(2, flightId);
                updateSeatsStatement.executeUpdate();

                // Insert booking record
                String bookingQuery = "INSERT INTO bookings (flight_id, passenger_name, seat_class, num_passengers, booking_date) VALUES (?, ?, ?, ?, ?)";
                bookingStatement = connection.prepareStatement(bookingQuery);
                bookingStatement.setInt(1, flightId);
                bookingStatement.setString(2, passengerName);
                bookingStatement.setString(3, seatClass);
                bookingStatement.setInt(4, numPassengers);
                java.sql.Date sqlDate = java.sql.Date.valueOf(journeyDate);
                bookingStatement.setDate(5, sqlDate);

                int rowsInserted = bookingStatement.executeUpdate();
                if (rowsInserted > 0) {
                    connection.commit(); // Commit transaction
                    sendSweetAlert(response, "Booking Successful!", 
                        "Flight ID: " + flightId + "<br>" +
                        "Passenger Name: " + passengerName + "<br>" +
                        "Source City: " + sourceCity + "<br>" +
                        "Destination City: " + destinationCity + "<br>" +
                        "Departure Time: " + departureTime + "<br>" +
                        "Arrival Time: " + arrivalTime + "<br>" +
                        "Number of Passengers: " + numPassengers + "<br>" +
                        "Date of Journey: " + journeyDate + "<br>" +
                        "Total Amount: " + totalAmount, "success");
                } else {
                    connection.rollback(); // Rollback transaction if booking fails
                    sendSweetAlert(response, "Error", "Booking failed. Please try again.", "error");
                }
            } else {
                sendSweetAlert(response, "Error", "Flight not found.", "error");
                connection.rollback();
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback transaction in case of error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            sendSweetAlert(response, "Error", "Booking failed! Please try again.", "error");
        } finally {
            // Close resources
            try {
                if (flightResultSet != null) flightResultSet.close();
                if (flightStatement != null) flightStatement.close();
                if (updateSeatsStatement != null) updateSeatsStatement.close();
                if (bookingStatement != null) bookingStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendSweetAlert(HttpServletResponse response, String title, String text, String icon) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css'>");
        out.println("</head><body>");
        out.println("<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.js'></script>");
        out.println("<script>");
        out.println("Swal.fire({");
        out.println("  title: '" + title + "',");
        out.println("  html: '" + text.replace("\n", "<br>") + "',");
        out.println("  icon: '" + icon + "',");
        out.println("  confirmButtonText: 'OK'");
        out.println("}).then((result) => {");
        out.println("  if (result.isConfirmed) {");
        out.println("    window.location.href = 'index.jsp';");
        out.println("  }");
        out.println("});");
        out.println("</script>");
        out.println("</body></html>");
    }
}
