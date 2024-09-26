package com.uniquedeveloper.registration;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginiValidationServlet")
public class LoginiValidationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/project";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "Lingumysql@45";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement pstmt = null;
        RequestDispatcher dispatcher = null;

        try {
     
            Class.forName("com.mysql.cj.jdbc.Driver");

            String hashedPassword = hashPassword(password);

            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            
            String sql = "SELECT * FROM register_login WHERE email = ? AND PassKey = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, hashedPassword); 
            
            ResultSet rows = pstmt.executeQuery();

            response.setContentType("text/html");
            HttpSession session = request.getSession();
            
            if (rows.next()) {
                session.setAttribute("email", email);
                session.setAttribute("name", rows.getString("first_name"));
                session.setAttribute("PassKey", hashedPassword);
                response.sendRedirect("index.jsp");
            } else {
            	request.setAttribute("status","failed");
				dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
            }
        } catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes); 
    }
}