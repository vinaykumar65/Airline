<%-- <%
    if(session.getAttribute("name")==null){
        response.sendRedirect("login.jsp");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Airline Reservation System</title>
    <style>
        /* Basic reset for consistent styling */
        body, h1, form {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }

        /* Background styling */
        body {
            background-color: #f0f0f0; /* Fallback color */
            background-size: cover;
            background-repeat: no-repeat;
            background-position: center;
            display: flex;
            flex-direction: column;
            align-items: center;
            height: 100vh;
            color: #fff;
        }

        /* Container styling */
        .container {
            display: flex;
            width: 80%;
            max-width: 1200px;
            margin: auto;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        /* Left side container styling */
        .left-container {
            background-color: rgba(0, 1, 0, 0.7); /* Semi-transparent background */
            padding: 70px 40px;
            border-radius: 10px;
            width: 25%;
            margin-right: 10px;
        }

        /* Right side container styling */
        .right-container {
            background-color: rgba(255, 255, 255, 0.5); /* Semi-transparent background */
            padding: 50px 40px;
            border-radius: 10px;
            width: 60%;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        /* Heading styling */
        h1 {
            margin-bottom: 20px;
            font-size: 36px;
            color: #f2f2f2;
        }

        /* Form and button styling */
        form {
            margin-top: 20px;
        }

        input[type="submit"] {
            background-color: #ff7f50;
            color: white;
            border: none;
            padding: 15px 30px;
            font-size: 18px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.3s ease;
            margin-top: 10px;
        }

        /* Hover effect for buttons */
        input[type="submit"]:hover {
            background-color: #ff6347;
            transform: scale(1.05);
        }

        /* Image styling in the right container */
        .right-container img {
            width: 150%; /* Adjust width to make the image larger */
            height: 110%;
            border-radius: 10px;
        }

        /* Styling for the "Click to fly" line */
        .header-line {
            text-align: center;
            font-size: 28px;
            font-weight: bold;
            color: #fff;
            background: linear-gradient(135deg, #ff7f50, #ff6347);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            border: 2px solid #fff;
            padding: 10px 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
            margin: 20px 0;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
    </style>
</head>
<body>

    <div class="header-line">Click to fly</div>
    
    <div class="container">
        <div class="left-container">
            <h1>Airline Reservation System</h1>

            <form action="DomesticFlights" method="get">
                <input type="submit" value="View Domestic Flights">
            </form>

            <form action="InternationalFlights" method="get">
                <input type="submit" value="View International Flights">
            </form>
        </div>
        <div class="right-container">
            <img src="https://t3.ftcdn.net/jpg/09/43/76/58/240_F_943765895_ll0Cqn50l2iNXj6bIAziVkiCGTl2Ucc0.jpg" alt="Airline Image">
        </div>
    </div>
</body>
</html>  --%>




<%
    if(session.getAttribute("name") == null) {
        response.sendRedirect("login.jsp");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Airline Reservation System</title>
    <style>
        /* Basic reset for consistent styling */
        body, h1, form {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }

        /* Background styling */
        body {
            background-color: #f0f0f0; /* Fallback color */
            background-size: cover;
            background-repeat: no-repeat;
            background-position: center;
            display: flex;
            flex-direction: column;
            align-items: center;
            height: 100vh;
            color: #fff;
        }

        /* Container styling */
        .container {
            display: flex;
            width: 80%;
            max-width: 1200px;
            margin: auto;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        /* Left side container styling */
        .left-container {
            background-color: rgba(0, 1, 0, 0.7); /* Semi-transparent background */
            padding: 70px 40px;
            border-radius: 10px;
            width: 25%;
            margin-right: 10px;
        }

        /* Right side container styling */
        .right-container {
            background-color: rgba(255, 255, 255, 0.5); /* Semi-transparent background */
            padding: 50px 40px;
            border-radius: 10px;
            width: 60%;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        /* Heading styling */
        h1 {
            margin-bottom: 20px;
            font-size: 36px;
            color: #f2f2f2;
        }

        /* Form and button styling */
        form {
            margin-top: 20px;
        }

        input[type="submit"] {
            background-color: #ff7f50;
            color: white;
            border: none;
            padding: 15px 30px;
            font-size: 18px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.3s ease;
            margin-top: 10px;
        }

        /* Hover effect for buttons */
        input[type="submit"]:hover {
            background-color: #ff6347;
            transform: scale(1.05);
        }

        /* Image styling in the right container */
        .right-container img {
            width: 150%; /* Adjust width to make the image larger */
            height: 110%;
            border-radius: 10px;
        }

        /* Styling for the "Click to fly" line */
        .header-line {
            text-align: center;
            font-size: 28px;
            font-weight: bold;
            color: #fff;
            background: linear-gradient(135deg, #ff7f50, #ff6347);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            border: 2px solid #fff;
            padding: 10px 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
            margin: 20px 0;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        /* User info styling */
        .user-info {
            position: absolute;
            top: 20px;
            right: 20px;
            background: rgba(0, 0, 0, 0.6);
            padding: 10px;
            border-radius: 5px;
            color: #fff;
            font-size: 16px;
            display: flex;
            align-items: center;
        }

        .user-info button {
            background-color: #ff7f50;
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 14px;
            border-radius: 5px;
            cursor: pointer;
            margin-left: 10px;
            transition: background-color 0.3s ease;
        }

        .user-info button:hover {
            background-color: #ff6347;
        }
    </style>
</head>
<body>
    <div class="user-info">
        <span><%= session.getAttribute("name") %></span>
        <form action="logout" method="post">
            <button type="submit">Logout</button>
        </form>
    </div>

    <div class="header-line">"Book Your Wings to the World"</div>
    
    <div class="container">
        <div class="left-container">
            <h1>Airline Reservation System</h1>

            <form action="DomesticFlights" method="get">
                <input type="submit" value="View Domestic Flights">
            </form>

            <form action="InternationalFlights" method="get">
                <input type="submit" value="View International Flights">
            </form>
        </div>
        <div class="right-container">
            <img src="https://t3.ftcdn.net/jpg/09/43/76/58/240_F_943765895_ll0Cqn50l2iNXj6bIAziVkiCGTl2Ucc0.jpg" alt="Airline Image">
        </div>
    </div>
</body>
</html>
