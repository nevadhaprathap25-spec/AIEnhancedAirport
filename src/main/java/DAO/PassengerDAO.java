package DAO;

import DB.DBConnection;
import Model.Passenger;
import Model.Login;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

public class PassengerDAO {

    // ================= Passenger Login =================

	public Passenger login(Login login) {

	    Passenger passenger = null;

	    try {

	        Connection con = DBConnection.getConnection();

	        String sql = "SELECT * FROM passenger WHERE email=?";

	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setString(1, login.getEmail());

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {

	            passenger = new Passenger();

	            passenger.setPassengerId(rs.getInt("passenger_id"));
	            passenger.setFirstName(rs.getString("first_name"));
	            passenger.setLastName(rs.getString("last_name"));
	            passenger.setEmail(rs.getString("email"));

	        }

	        rs.close();
	        ps.close();
	        con.close();

	    } catch (Exception e) {

	        e.printStackTrace();

	    }

	    return passenger;
	}
    // ================= Add Passenger =================

    public boolean addPassenger(Passenger passenger) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO passenger "
                    + "(first_name,last_name,gender,age,passport_no,"
                    + "nationality,phone,email,address,flight_id,"
                    + "seat_no,travel_class,ticket_no,"
                    + "booking_date,journey_date,status)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, passenger.getFirstName());
            ps.setString(2, passenger.getLastName());
            ps.setString(3, passenger.getGender());
            ps.setString(5, passenger.getPassportNo());
            ps.setInt(4, passenger.getAge());
            ps.setString(6, passenger.getNationality());
            ps.setString(6, passenger.getNationality());
            ps.setString(7, passenger.getPhone());
            ps.setString(8, passenger.getEmail());
            ps.setString(9, passenger.getAddress());
            ps.setInt(10, passenger.getFlightId());
            ps.setString(11, passenger.getSeatNo());
            ps.setString(12, passenger.getTravelClass());
            ps.setString(13, passenger.getTicketNo());
            ps.setString(14, passenger.getBookingDate());
            ps.setString(15, passenger.getJourneyDate());
            ps.setString(16, passenger.getStatus());

            status = ps.executeUpdate() > 0;

            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(e.getMessage());
            e.printStackTrace();

        }

        return status;

    }
    // ================= Get All Passengers =================

    public List<Passenger> getAllPassengers() {

        List<Passenger> list = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM passenger ORDER BY passenger_id";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Passenger passenger = new Passenger();

                passenger.setPassengerId(rs.getInt("passenger_id"));
                passenger.setFirstName(rs.getString("first_name"));
                passenger.setLastName(rs.getString("last_name"));
                passenger.setGender(rs.getString("gender"));
                passenger.setAge(rs.getInt("age"));
                passenger.setPassportNo(rs.getString("passport_no"));
                passenger.setNationality(rs.getString("nationality"));
                passenger.setPhone(rs.getString("phone"));
                passenger.setEmail(rs.getString("email"));
                passenger.setAddress(rs.getString("address"));
                passenger.setFlightId(rs.getInt("flight_id"));
                passenger.setSeatNo(rs.getString("seat_no"));
                passenger.setTravelClass(rs.getString("travel_class"));
                passenger.setTicketNo(rs.getString("ticket_no"));
                passenger.setBookingDate(rs.getString("booking_date"));
                passenger.setJourneyDate(rs.getString("journey_date"));
                passenger.setStatus(rs.getString("status"));

                list.add(passenger);

            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return list;

    }

    // ================= Get Passenger By ID =================

    public Passenger getPassengerById(int id) {

        Passenger passenger = null;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM passenger WHERE passenger_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                passenger = new Passenger();

                passenger.setPassengerId(rs.getInt("passenger_id"));
                passenger.setFirstName(rs.getString("first_name"));
                passenger.setLastName(rs.getString("last_name"));
                passenger.setGender(rs.getString("gender"));
                passenger.setAge(rs.getInt("age"));
                passenger.setPassportNo(rs.getString("passport_no"));
                passenger.setNationality(rs.getString("nationality"));
                passenger.setPhone(rs.getString("phone"));
                passenger.setEmail(rs.getString("email"));
                passenger.setAddress(rs.getString("address"));
                passenger.setFlightId(rs.getInt("flight_id"));
                passenger.setSeatNo(rs.getString("seat_no"));
                passenger.setTravelClass(rs.getString("travel_class"));
                passenger.setTicketNo(rs.getString("ticket_no"));
                passenger.setBookingDate(rs.getString("booking_date"));
                passenger.setJourneyDate(rs.getString("journey_date"));
                passenger.setStatus(rs.getString("status"));

            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return passenger;

    }
    // ================= Update Passenger =================

    public boolean updatePassenger(Passenger passenger) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "UPDATE passenger SET "
                    + "first_name=?,"
                    + "last_name=?,"
                    + "gender=?,"
                    + "age=?,"
                    + "passport_no=?,"
                    + "nationality=?,"
                    + "phone=?,"
                    + "email=?,"
                    + "address=?,"
                    + "flight_id=?,"
                    + "seat_no=?,"
                    + "travel_class=?,"
                    + "ticket_no=?,"
                    + "booking_date=?,"
                    + "journey_date=?,"
                    + "status=? "
                    + "WHERE passenger_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, passenger.getFirstName());
            ps.setString(2, passenger.getLastName());
            ps.setString(3, passenger.getGender());
            ps.setInt(4, passenger.getAge());
            ps.setString(5, passenger.getPassportNo());
            ps.setString(6, passenger.getNationality());
            ps.setString(7, passenger.getPhone());
            ps.setString(8, passenger.getEmail());
            ps.setString(9, passenger.getAddress());
            ps.setInt(10, passenger.getFlightId());
            ps.setString(11, passenger.getSeatNo());
            ps.setString(12, passenger.getTravelClass());
            ps.setString(13, passenger.getTicketNo());
            ps.setString(14, passenger.getBookingDate());
            ps.setString(15, passenger.getJourneyDate());
            ps.setString(16, passenger.getStatus());
            ps.setInt(17, passenger.getPassengerId());

            status = ps.executeUpdate() > 0;

            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return status;

    }

    // ================= Delete Passenger =================

    public boolean deletePassenger(int id) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM passenger WHERE passenger_id=?";

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
    
    public Map<String, Object> getPassengerTicket(int passengerId) {

        Map<String, Object> ticket = new HashMap<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql =
            	    "SELECT "
            	  + "b.booking_id,"
            	  + "b.booking_status,"
            	  + "CONCAT(p.first_name,' ',p.last_name) AS passenger_name,"
            	  + "p.seat_no,"
            	  + "f.flight_number,"
            	  + "f.airline,"
            	  
            	  + "f.source,"
            	  + "f.destination,"
            	  + "TIME_FORMAT(f.departure_time,'%h:%i %p') AS departure_time,"
            	  + "HOUR(f.departure_time) AS departure_hour,"
            	  + "TIME_FORMAT(f.arrival_time,'%h:%i %p') AS arrival_time,"
            	  + "f.status AS flight_status,"
            	  + "f.terminal,"
            	  + "f.gate_number,"
            	  + "TIME_FORMAT(f.boarding_time,'%h:%i %p') AS boarding_time "
            	  + "FROM booking b "
            	  + "INNER JOIN passenger p ON b.passenger_id = p.passenger_id "
            	  + "INNER JOIN flights f ON b.flight_id = f.flight_id "
            	  + "WHERE p.passenger_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, passengerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                ticket.put("bookingId", rs.getInt("booking_id"));
                
                ticket.put("bookingStatus", rs.getString("booking_status"));

                ticket.put("passengerName", rs.getString("passenger_name"));
                ticket.put("seatNo", rs.getString("seat_no"));

                ticket.put("flightNumber", rs.getString("flight_number"));
                ticket.put("airline", rs.getString("airline"));
                ticket.put("source", rs.getString("source"));
                ticket.put("destination", rs.getString("destination"));
                
                ticket.put("passengerId", passengerId);

                ticket.put("departureTime", rs.getString("departure_time"));
                ticket.put("departureHour", rs.getInt("departure_hour"));
                ticket.put("arrivalTime", rs.getString("arrival_time"));
                ticket.put("flightStatus", rs.getString("flight_status"));
                
                ticket.put("terminal", rs.getString("terminal"));
                ticket.put("gateNumber", rs.getString("gate_number"));
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return ticket;

    }

}