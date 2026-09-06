package DAO;

import DB.DBConnection;
import Model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    // ================= ADD BOOKING =================

    public boolean addBooking(Booking booking) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "INSERT INTO booking "
                  + "(passenger_id, flight_id, booking_date, seat_number, booking_status) "
                  + "VALUES (?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, booking.getPassengerId());
            ps.setInt(2, booking.getFlightId());
            ps.setString(3, booking.getBookingDate());
            ps.setString(4, booking.getSeatNumber());
            ps.setString(5, booking.getBookingStatus());

            status = ps.executeUpdate() > 0;

            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return status;

    }

    // ================= GET ALL BOOKINGS =================

    public List<Booking> getAllBookings() {

        List<Booking> list = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT "
                  + "b.booking_id, "
                  + "b.passenger_id, "
                  + "b.flight_id, "
                  + "CONCAT(p.first_name,' ',p.last_name) passenger_name, "
                  + "f.flight_number, "
                  + "b.booking_date, "
                  + "b.seat_number, "
                  + "b.booking_status "
                  + "FROM booking b "
                  + "INNER JOIN passenger p "
                  + "ON b.passenger_id=p.passenger_id "
                  + "INNER JOIN flights f "
                  + "ON b.flight_id=f.flight_id "
                  + "ORDER BY b.booking_id";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Booking booking = new Booking();

                booking.setBookingId(
                        rs.getInt("booking_id"));

                booking.setPassengerId(
                        rs.getInt("passenger_id"));

                booking.setFlightId(
                        rs.getInt("flight_id"));

                booking.setPassengerName(
                        rs.getString("passenger_name"));

                booking.setFlightNumber(
                        rs.getString("flight_number"));

                booking.setBookingDate(
                        rs.getString("booking_date"));

                booking.setSeatNumber(
                        rs.getString("seat_number"));

                booking.setBookingStatus(
                        rs.getString("booking_status"));

                list.add(booking);

            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return list;

    }
    
    // ================= GET BOOKING BY ID =================

    public Booking getBookingById(int id) {

        Booking booking = null;

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT * FROM booking WHERE booking_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                booking = new Booking();

                booking.setBookingId(
                        rs.getInt("booking_id"));

                booking.setPassengerId(
                        rs.getInt("passenger_id"));

                booking.setFlightId(
                        rs.getInt("flight_id"));

                booking.setBookingDate(
                        rs.getString("booking_date"));

                booking.setSeatNumber(
                        rs.getString("seat_number"));

                booking.setBookingStatus(
                        rs.getString("booking_status"));

            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return booking;

    }


    // ================= UPDATE BOOKING =================

    public boolean updateBooking(Booking booking) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "UPDATE booking SET "
                  + "passenger_id=?, "
                  + "flight_id=?, "
                  + "booking_date=?, "
                  + "seat_number=?, "
                  + "booking_status=? "
                  + "WHERE booking_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, booking.getPassengerId());
            ps.setInt(2, booking.getFlightId());
            ps.setString(3, booking.getBookingDate());
            ps.setString(4, booking.getSeatNumber());
            ps.setString(5, booking.getBookingStatus());
            ps.setInt(6, booking.getBookingId());

            status = ps.executeUpdate() > 0;

            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return status;

    }


    // ================= DELETE BOOKING =================

    public boolean deleteBooking(int id) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "DELETE FROM booking WHERE booking_id=?";

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
    
    // ================= GET ALL PASSENGERS =================

    public List<Booking> getAllPassengers() {

        List<Booking> list = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT passenger_id, "
                  + "CONCAT(first_name,' ',last_name) AS passenger_name "
                  + "FROM passenger "
                  + "ORDER BY first_name";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Booking booking = new Booking();

                booking.setPassengerId(
                        rs.getInt("passenger_id"));

                booking.setPassengerName(
                        rs.getString("passenger_name"));

                list.add(booking);

            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return list;

    }


    // ================= GET ALL FLIGHTS =================

    public List<Booking> getAllFlights() {

        List<Booking> list = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT flight_id, flight_number "
                  + "FROM flights "
                  + "ORDER BY flight_number";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Booking booking = new Booking();

                booking.setFlightId(
                        rs.getInt("flight_id"));

                booking.setFlightNumber(
                        rs.getString("flight_number"));

                list.add(booking);

            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return list;

    }

}