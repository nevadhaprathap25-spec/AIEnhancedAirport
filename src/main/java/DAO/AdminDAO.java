package DAO;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.Gson;

import DB.DBConnection;
import Model.Login;
import Model.Dashboard;

public class AdminDAO {

    Connection con;

    public String login(Login login) {

        con = DBConnection.getConnection();

        try {
        	
        	if (con == null) {
        	    System.out.println("Connection is NULL");
        	    return "INVALID";
        	}

            // Check Admin
            String sql =
            "SELECT * FROM admin WHERE username=? AND password=?";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setString(1, login.getUsername());
            ps.setString(2, login.getPassword());

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                return "ADMIN";

            }


        }
        catch(Exception e) {

            e.printStackTrace();

        }

        return "INVALID";

    }
    
    public String getDashboardData() {

        List<Dashboard> list = new ArrayList<>();

        con = DBConnection.getConnection();

        try {

        	 String sql =
                     "SELECT b.booking_id,"
                   + "CONCAT(p.first_name,' ',p.last_name) passenger,"
                   + "f.flight_number,"
                   + "f.airline,"
                   + "f.source,"
                   + "f.destination,"
                   + "TIME_FORMAT(f.departure_time,'%h:%i %p') departure,"
                   + "TIME_FORMAT(f.arrival_time,'%h:%i %p') arrival,"
                   + "b.seat_number,"
                   + "b.booking_status,"
                   + "DATE_FORMAT(b.booking_date,'%d-%m-%Y') booking_date "
                   + "FROM booking b "
                   + "INNER JOIN passenger p ON b.passenger_id=p.passenger_id "
                   + "INNER JOIN flights f ON b.flight_id=f.flight_id "
                   + "WHERE b.booking_date=CURDATE() "
                   + "ORDER BY f.departure_time";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Dashboard dashboard = new Dashboard();

                dashboard.setPassenger(rs.getString("passenger"));
                dashboard.setFlightNumber(rs.getString("flight_number"));
                dashboard.setAirline(rs.getString("airline"));
                dashboard.setSource(rs.getString("source"));
                dashboard.setDestination(rs.getString("destination"));
                dashboard.setDeparture(rs.getString("departure"));
                dashboard.setSeatNo(rs.getString("seat_number"));
                dashboard.setBookingStatus(rs.getString("booking_status"));
                
                list.add(dashboard);
            }
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Gson().toJson(list);
    }
}