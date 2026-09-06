package MainServlet;

import DAO.FlightDAO;
import Model.Flight;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FlightServlet")
public class FlightServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    FlightDAO dao = new FlightDAO();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
    	
    	

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        if (action == null)
            action = "list";

        switch (action) {

        case "list":

            List<Flight> list = dao.getAllFlights();

            out.print("[");

            for (int i = 0; i < list.size(); i++) {

                Flight f = list.get(i);

                out.print("{");

                out.print("\"flightId\":" + f.getFlightId() + ",");
                out.print("\"flightNumber\":\"" + f.getFlightNumber() + "\",");
                out.print("\"airline\":\"" + f.getAirline() + "\",");
                out.print("\"source\":\"" + f.getSource() + "\",");
                out.print("\"destination\":\"" + f.getDestination() + "\",");
                out.print("\"departureTime\":\"" + f.getDepartureTime() + "\",");
                out.print("\"arrivalTime\":\"" + f.getArrivalTime() + "\",");
                out.print("\"totalSeats\":" + f.getTotalSeats() + ",");
                out.print("\"availableSeats\":" + f.getAvailableSeats() + ",");
                out.print("\"status\":\"" + f.getStatus() + "\"");

                out.print("}");

                if (i < list.size() - 1)
                    out.print(",");

            }

            out.print("]");

            break;

        case "delete":

            int id = Integer.parseInt(request.getParameter("id"));

            if (dao.deleteFlight(id))
                out.print("Deleted Successfully");
            else
                out.print("Delete Failed");

            break;

        case "get":

            int flightId = Integer.parseInt(request.getParameter("id"));

            Flight f = dao.getFlightById(flightId);

            if (f != null) {

                out.print("{");

                out.print("\"flightId\":" + f.getFlightId() + ",");
                out.print("\"flightNumber\":\"" + f.getFlightNumber() + "\",");
                out.print("\"airline\":\"" + f.getAirline() + "\",");
                out.print("\"source\":\"" + f.getSource() + "\",");
                out.print("\"destination\":\"" + f.getDestination() + "\",");
                out.print("\"departureTime\":\"" + f.getDepartureTime() + "\",");
                out.print("\"arrivalTime\":\"" + f.getArrivalTime() + "\",");
                out.print("\"totalSeats\":" + f.getTotalSeats() + ",");
                out.print("\"availableSeats\":" + f.getAvailableSeats() + ",");
                out.print("\"status\":\"" + f.getStatus() + "\"");

                out.print("}");

            } else {

                out.print("{}");

            }

            break;

        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

    	try {
    	//	System.out.println(request.getRequestURI());
    		response.setContentType("text/plain");

            String action = request.getParameter("action");

            Flight flight = new Flight();
            
            flight.setFlightNumber(request.getParameter("flightNumber"));
            flight.setAirline(request.getParameter("airline"));
            flight.setSource(request.getParameter("source"));
            flight.setDestination(request.getParameter("destination"));
            flight.setDepartureTime(request.getParameter("departureTime"));
            flight.setArrivalTime(request.getParameter("arrivalTime"));
            flight.setTotalSeats(Integer.parseInt(request.getParameter("totalSeats")));
            flight.setAvailableSeats(Integer.parseInt(request.getParameter("availableSeats")));
            flight.setStatus(request.getParameter("status"));

            if ("add".equals(action)) {

                if (dao.addFlight(flight))
                	response.sendRedirect("UI/FlightDetails.html?msg=added");
                else
                	response.sendRedirect("UI/FlightDetails.html?msg=failed");

            } else if ("update".equals(action)) {

                flight.setFlightId(Integer.parseInt(request.getParameter("flightId")));

                if (dao.updateFlight(flight))
                	response.sendRedirect("UI/FlightDetails.html?msg=updated");
                else
                	response.sendRedirect("UI/FlightDetails.html?msg=failed");

            }
            else if ("delete".equals(action)) {

                int flightId = Integer.parseInt(request.getParameter("flightId"));

                if (dao.deleteFlight(flightId))
                	response.sendRedirect("UI/FlightDetails.html?msg=deleted");
                else
                	response.sendRedirect("UI/FlightDetails.html?msg=failed");
            }

    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
        
    }

}