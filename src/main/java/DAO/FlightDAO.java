package DAO;

import DB.DBConnection;
import Model.Flight;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO {

    // Add Flight
    public boolean addFlight(Flight flight) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO flights "
                    + "(flight_number, airline, source, destination, departure_time, arrival_time, total_seats, available_seats, status) "
                    + "VALUES (?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, flight.getFlightNumber());
            ps.setString(2, flight.getAirline());
            ps.setString(3, flight.getSource());
            ps.setString(4, flight.getDestination());
            ps.setString(5, flight.getDepartureTime());
            ps.setString(6, flight.getArrivalTime());
            ps.setInt(7, flight.getTotalSeats());
            ps.setInt(8, flight.getAvailableSeats());
            ps.setString(9, flight.getStatus());

            status = ps.executeUpdate() > 0;

            ps.close();
            con.close();

        } catch (Exception e) {
        	System.out.println(e.getMessage());
            e.printStackTrace();

        }

        return status;
    }

    // Get All Flights
    public List<Flight> getAllFlights() {

        List<Flight> list = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM flights ORDER BY flight_id";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Flight flight = new Flight();

                flight.setFlightId(rs.getInt("flight_id"));
                flight.setFlightNumber(rs.getString("flight_number"));
                flight.setAirline(rs.getString("airline"));
                flight.setSource(rs.getString("source"));
                flight.setDestination(rs.getString("destination"));
                flight.setDepartureTime(rs.getString("departure_time"));
                flight.setArrivalTime(rs.getString("arrival_time"));
                flight.setTotalSeats(rs.getInt("total_seats"));
                flight.setAvailableSeats(rs.getInt("available_seats"));
                flight.setStatus(rs.getString("status"));

                list.add(flight);

            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return list;

    }

    // Get Flight By ID
    public Flight getFlightById(int id) {

        Flight flight = null;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM flights WHERE flight_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                flight = new Flight();

                flight.setFlightId(rs.getInt("flight_id"));
                flight.setFlightNumber(rs.getString("flight_number"));
                flight.setAirline(rs.getString("airline"));
                flight.setSource(rs.getString("source"));
                flight.setDestination(rs.getString("destination"));
                flight.setDepartureTime(rs.getString("departure_time"));
                flight.setArrivalTime(rs.getString("arrival_time"));
                flight.setTotalSeats(rs.getInt("total_seats"));
                flight.setAvailableSeats(rs.getInt("available_seats"));
                flight.setStatus(rs.getString("status"));

            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return flight;

    }

    // Update Flight
    public boolean updateFlight(Flight flight) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "UPDATE flights SET "
                    + "flight_number=?, airline=?, source=?, destination=?, "
                    + "departure_time=?, arrival_time=?, total_seats=?, "
                    + "available_seats=?, status=? "
                    + "WHERE flight_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, flight.getFlightNumber());
            ps.setString(2, flight.getAirline());
            ps.setString(3, flight.getSource());
            ps.setString(4, flight.getDestination());
            ps.setString(5, flight.getDepartureTime());
            ps.setString(6, flight.getArrivalTime());
            ps.setInt(7, flight.getTotalSeats());
            ps.setInt(8, flight.getAvailableSeats());
            ps.setString(9, flight.getStatus());
            ps.setInt(10, flight.getFlightId());

            status = ps.executeUpdate() > 0;

            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return status;

    }

    // Delete Flight
    public boolean deleteFlight(int id) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM flights WHERE flight_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            status = ps.executeUpdate() > 0;

            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return status;

    }

}