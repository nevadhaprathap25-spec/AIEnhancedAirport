package MainServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import DAO.PassengerDAO;
import Model.Login;
import Model.Passenger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import jakarta.servlet.http.HttpSession;

@WebServlet("/PassengerServlet")
public class PassengerServlet extends HttpServlet {

    PassengerDAO dao = new PassengerDAO();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("list".equals(action)) {

            List<Passenger> list = dao.getAllPassengers();

            response.setContentType("application/json");

            PrintWriter out = response.getWriter();

            out.print(new Gson().toJson(list));

            out.flush();

        }

        else if ("get".equals(action)) {
        	
        	

            int id = Integer.parseInt(request.getParameter("id"));
            

            Passenger passenger = dao.getPassengerById(id);

            response.setContentType("application/json");

            PrintWriter out = response.getWriter();

            out.print(new Gson().toJson(passenger));

            out.flush();

        }

        else if ("delete".equals(action)) {

            int id = Integer.parseInt(request.getParameter("id"));

            boolean status = dao.deletePassenger(id);

            if (status) {

                response.getWriter().print("Passenger Deleted Successfully");

            } else {

                response.getWriter().print("Unable to Delete Passenger");

            }

        }
        
        else if ("myTicket".equals(action)) {

            HttpSession session = request.getSession(false);

            if (session == null ||
                session.getAttribute("passengerId") == null) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;

            }

            int passengerId =
                    (Integer) session.getAttribute("passengerId");

            Map<String, Object> ticket =
                    dao.getPassengerTicket(passengerId);

            response.setContentType("application/json");

            response.getWriter().print(new Gson().toJson(ticket));

        }
        
        else if ("predictDelay".equals(action)) {

            String airline = request.getParameter("airline");
            String source = request.getParameter("source");
            String destination = request.getParameter("destination");
            int departureHour = Integer.parseInt(request.getParameter("departureHour"));
            String weather = request.getParameter("weather");
            String holiday = request.getParameter("holiday");
            int previousDelay = Integer.parseInt(request.getParameter("previousDelay"));

            try {

                URL url = new URL("http://127.0.0.1:5000/predict");

                HttpURLConnection con =
                        (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);
                
                System.out.println("===== AI Prediction Request =====");
                System.out.println("Airline : " + airline);
                System.out.println("Source : " + source);
                System.out.println("Destination : " + destination);
                System.out.println("Departure Hour : " + departureHour);
                System.out.println("Weather : " + weather);
                System.out.println("Holiday : " + holiday);
                System.out.println("Previous Delay : " + previousDelay);

                Map<String, Object> json = new HashMap<>();

                json.put("Airline", airline);
                json.put("Source", source);
                json.put("Destination", destination);
                json.put("DepartureHour", departureHour);
                json.put("Weather", weather);
                json.put("Holiday", holiday);
                json.put("PreviousDelay", previousDelay);

                String jsonString = new Gson().toJson(json);

                OutputStream os = con.getOutputStream();

                os.write(jsonString.getBytes("UTF-8"));
                os.flush();
                os.close();

                int responseCode = con.getResponseCode();

                BufferedReader br;

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    br = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));

                } else {

                    br = new BufferedReader(
                            new InputStreamReader(con.getErrorStream()));

                }

                StringBuilder result = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    result.append(line);
                }

                br.close();
                
                System.out.println("===== AI Response =====");
                System.out.println(result.toString());

                response.setContentType("application/json");
                response.getWriter().print(result.toString());

            } catch (Exception e) {

                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", e.getMessage());

                response.setContentType("application/json");
                response.getWriter().print(new Gson().toJson(error));
            }
        }

    }
    
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
    	
    	try {
    		   String action = request.getParameter("action");

    		  if (action == null || action.trim().equals("")) {

    	            String email = request.getParameter("email");

    	            Login login = new Login();

    	            login.setEmail(email);

    	            Passenger passenger = dao.login(login);

    	            if (passenger != null) {

    	                HttpSession session = request.getSession();

    	                session.setAttribute("passengerId",
    	                        passenger.getPassengerId());

    	                session.setAttribute("passengerName",
    	                        passenger.getFirstName() + " " + passenger.getLastName());
    	                
    	              

    	                response.sendRedirect("UI/PassengerDashboard.html");

    	            } else {

    	                response.getWriter().println("<h2>Invalid Username</h2>");

    	            }
    	        }

    	        // ================= ADD PASSENGER =================

    	        else if (action.equals("add")) {

    	            Passenger passenger = new Passenger();

    	            passenger.setFirstName(request.getParameter("firstName"));
    	            passenger.setLastName(request.getParameter("lastName"));
    	            passenger.setGender(request.getParameter("gender"));
    	            passenger.setAge(18);
    	            passenger.setNationality("Indian");
    	            passenger.setPassportNo(request.getParameter("passportNo"));
    	            passenger.setPhone(request.getParameter("phone"));
    	            passenger.setEmail(request.getParameter("email"));
    	            passenger.setAddress(request.getParameter("address"));
    	            passenger.setFlightId(Integer.parseInt(request.getParameter("flightId")));
    	            passenger.setSeatNo(request.getParameter("seatNo"));
    	            passenger.setTravelClass(request.getParameter("travelClass"));
    	            passenger.setTicketNo(request.getParameter("ticketNo"));
    	            passenger.setBookingDate(request.getParameter("bookingDate"));
    	            passenger.setJourneyDate(request.getParameter("journeyDate"));
    	            passenger.setStatus(request.getParameter("status"));

    	            boolean status = dao.addPassenger(passenger);

    	            if (status) {

    	                response.sendRedirect("UI/PassengerDetails.html?msg=added");

    	            } else {

    	                response.getWriter().println("Unable to Add Passenger");

    	            }

    	        }

    	        // ================= UPDATE PASSENGER =================

    	        else if (action.equals("update")) {

    	            Passenger passenger = new Passenger();

    	            passenger.setPassengerId(
    	                    Integer.parseInt(request.getParameter("passengerId")));

    	            passenger.setFirstName(request.getParameter("firstName"));
    	            passenger.setLastName(request.getParameter("lastName"));
    	            passenger.setGender(request.getParameter("gender"));
    	            passenger.setAge(18);
    	            passenger.setNationality("Indian");
    	            passenger.setPassportNo(request.getParameter("passportNo"));
    	            passenger.setNationality("Indian");
    	            passenger.setPhone(request.getParameter("phone"));
    	            passenger.setEmail(request.getParameter("email"));
    	            passenger.setAddress(request.getParameter("address"));
    	            passenger.setFlightId(Integer.parseInt(request.getParameter("flightId")));
    	            passenger.setSeatNo(request.getParameter("seatNo"));
    	            passenger.setTravelClass(request.getParameter("travelClass"));
    	            passenger.setTicketNo(request.getParameter("ticketNo"));
    	            passenger.setBookingDate(request.getParameter("bookingDate"));
    	            passenger.setJourneyDate(request.getParameter("journeyDate"));
    	            passenger.setStatus(request.getParameter("status"));

    	            boolean status = dao.updatePassenger(passenger);

    	            if (status) {

    	                response.sendRedirect("UI/PassengerDetails.html?msg=updated");

    	            } else {

    	                response.getWriter().println("Unable to Update Passenger");

    	            }

    	        }
    		  
    		// ================= AI FLIGHT DELAY PREDICTION =================

    	        else if (action.equals("predictDelay")) {

    	            String airline = request.getParameter("airline");
    	            String source = request.getParameter("source");
    	            String destination = request.getParameter("destination");
    	            int departureHour = Integer.parseInt(request.getParameter("departureHour"));
    	            String weather = request.getParameter("weather");
    	            String holiday = request.getParameter("holiday");
    	            int previousDelay = Integer.parseInt(request.getParameter("previousDelay"));

    	            try {

    	                URL url = new URL("http://127.0.0.1:5000/predict");

    	                HttpURLConnection con =
    	                        (HttpURLConnection) url.openConnection();

    	                con.setRequestMethod("POST");
    	                con.setRequestProperty("Content-Type", "application/json");
    	                con.setDoOutput(true);
    	                
    	                System.out.println("===== AI Prediction Request =====");
    	                System.out.println("Airline : " + airline);
    	                System.out.println("Source : " + source);
    	                System.out.println("Destination : " + destination);
    	                System.out.println("Departure Hour : " + departureHour);
    	                System.out.println("Weather : " + weather);
    	                System.out.println("Holiday : " + holiday);
    	                System.out.println("Previous Delay : " + previousDelay);

    	                Map<String, Object> json = new HashMap<>();

    	                json.put("Airline", airline);
    	                json.put("Source", source);
    	                json.put("Destination", destination);
    	                json.put("DepartureHour", departureHour);
    	                json.put("Weather", weather);
    	                json.put("Holiday", holiday);
    	                json.put("PreviousDelay", previousDelay);
    	                
    	              

    	                
    	                String jsonString = new Gson().toJson(json);

    	                OutputStream os = con.getOutputStream();

    	                os.write(jsonString.getBytes("UTF-8"));
    	                os.flush();
    	                os.close();

    	                int responseCode = con.getResponseCode();

    	                BufferedReader br;

    	                if (responseCode == HttpURLConnection.HTTP_OK) {

    	                    br = new BufferedReader(
    	                            new InputStreamReader(con.getInputStream()));

    	                } else {

    	                    br = new BufferedReader(
    	                            new InputStreamReader(con.getErrorStream()));

    	                }

    	                StringBuilder result = new StringBuilder();
    	                String line;

    	                while ((line = br.readLine()) != null) {
    	                    result.append(line);
    	                }

    	                br.close();
    	                
    	                System.out.println("===== AI Response =====");
    	                System.out.println(result.toString());

    	                response.setContentType("application/json");
    	                response.getWriter().print(result.toString());

    	            } catch (Exception e) {

    	            	Map<String, Object> error = new HashMap<>();
    	            	error.put("success", false);
    	            	error.put("message", e.getMessage());

    	            	response.setContentType("application/json");
    	            	response.getWriter().print(new Gson().toJson(error));
    	            }
    	        }
    		  
    	        else if (action.equals("chat")) {

    	            String message = request.getParameter("message");
    	            String flightNumber = request.getParameter("flightNumber");
    	            String passengerId = request.getParameter("passengerId");
    	            
    	            System.out.println("===== Chat Request =====");
    	            System.out.println("Message : " + message);
    	            System.out.println("Flight Number : " + flightNumber);
    	            System.out.println("Passenger ID : " + passengerId);

    	            try {

    	                URL url = new URL("http://127.0.0.1:5000/chat");

    	                HttpURLConnection con =
    	                        (HttpURLConnection) url.openConnection();

    	                con.setRequestMethod("POST");
    	                con.setRequestProperty("Content-Type", "application/json");
    	                con.setDoOutput(true);
    	                con.setConnectTimeout(10000);
    	                con.setReadTimeout(30000);

    	                Map<String, Object> json = new HashMap<>();

    	                json.put("message", message);
    	                json.put("flight_number", flightNumber);
    	                json.put("passenger_id", passengerId);

    	                String jsonString = new Gson().toJson(json);

    	                System.out.println("Sending JSON to AI API:");
    	                System.out.println(jsonString);

    	                OutputStream os = con.getOutputStream();
    	                os.write(jsonString.getBytes("UTF-8"));
    	                os.flush();
    	                os.close();

    	                int responseCode = con.getResponseCode();
    	                
    	                System.out.println("AI API Response Code: " + responseCode);

    	                BufferedReader br;

    	                if (responseCode == HttpURLConnection.HTTP_OK || 
    	                    responseCode == HttpURLConnection.HTTP_CREATED) {

    	                    br = new BufferedReader(
    	                            new InputStreamReader(con.getInputStream()));

    	                } else {

    	                    br = new BufferedReader(
    	                            new InputStreamReader(con.getErrorStream()));

    	                }

    	                StringBuilder result = new StringBuilder();
    	                String line;

    	                while ((line = br.readLine()) != null) {
    	                    result.append(line);
    	                }

    	                br.close();

    	                System.out.println("===== AI API Raw Response =====");
    	                System.out.println(result.toString());

    	                // Parse the response from Python API
    	                try {
    	                    Object responseObj = new com.google.gson.JsonParser()
    	                        .parse(result.toString());
    	                    
    	                    if (responseObj instanceof com.google.gson.JsonObject) {
    	                        com.google.gson.JsonObject jsonObj = 
    	                            (com.google.gson.JsonObject) responseObj;
    	                        
    	                        // Prepare response for frontend
    	                        Map<String, Object> finalResponse = new HashMap<>();
    	                        
    	                        // Check if response has the expected fields
    	                        if (jsonObj.has("response")) {
    	                            finalResponse.put("success", true);
    	                            finalResponse.put("response", 
    	                                jsonObj.get("response").getAsString());
    	                            finalResponse.put("message", 
    	                                jsonObj.get("response").getAsString());
    	                        } else if (jsonObj.has("reply")) {
    	                            finalResponse.put("success", true);
    	                            finalResponse.put("response", 
    	                                jsonObj.get("reply").getAsString());
    	                            finalResponse.put("message", 
    	                                jsonObj.get("reply").getAsString());
    	                        } else if (jsonObj.has("error")) {
    	                            finalResponse.put("success", false);
    	                            finalResponse.put("message", 
    	                                jsonObj.get("error").getAsString());
    	                        } else {
    	                            // If none of the expected fields exist, pass the whole response
    	                            finalResponse.put("success", true);
    	                            finalResponse.put("response", result.toString());
    	                            finalResponse.put("message", result.toString());
    	                        }
    	                        
    	                        response.setContentType("application/json");
    	                        response.getWriter()
    	                            .print(new Gson().toJson(finalResponse));
    	                    } else {
    	                        throw new Exception("Invalid JSON response");
    	                    }
    	                } catch (Exception parseEx) {
    	                    System.out.println("Error parsing API response: " + parseEx.getMessage());
    	                    
    	                    Map<String, Object> fallbackResponse = new HashMap<>();
    	                    fallbackResponse.put("success", false);
    	                    fallbackResponse.put("message", 
    	                        "Unable to understand AI response. Please try again.");
    	                    
    	                    response.setContentType("application/json");
    	                    response.getWriter()
    	                        .print(new Gson().toJson(fallbackResponse));
    	                }

    	            } catch(java.net.SocketTimeoutException timeoutEx) {

    	                System.out.println("Socket Timeout in chat: " + timeoutEx.getMessage());
    	                timeoutEx.printStackTrace();

    	                Map<String, Object> error = new HashMap<>();
    	                error.put("success", false);
    	                error.put("message", 
    	                    "AI Assistant is taking too long to respond. The AI service may be busy or not responding. Please try again in a moment.");

    	                response.setContentType("application/json");
    	                response.getWriter().print(new Gson().toJson(error));
    	                
    	            } catch(IOException ioEx) {

    	                System.out.println("IO Exception in chat: " + ioEx.getMessage());
    	                ioEx.printStackTrace();

    	                Map<String, Object> error = new HashMap<>();
    	                error.put("success", false);
    	                error.put("message", 
    	                    "Unable to connect to AI Assistant. Please ensure the AI service is running.");

    	                response.setContentType("application/json");
    	                response.getWriter().print(new Gson().toJson(error));
    	                
    	            } catch(Exception e) {

    	                System.out.println("General Exception in chat: " + e.getMessage());
    	                e.printStackTrace();

    	                Map<String, Object> error = new HashMap<>();
    	                error.put("success", false);
    	                error.put("message", "An error occurred: " + e.getMessage());

    	                response.setContentType("application/json");
    	                response.getWriter().print(new Gson().toJson(error));
    	            }

    	        }
    	}
    	
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    	}

     
        // ================= LOGIN =================

      
    }

}