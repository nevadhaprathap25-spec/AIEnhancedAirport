package MainServlet;

import DAO.BookingDAO;
import Model.Booking;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    BookingDAO dao = new BookingDAO();

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

        //===================== LIST BOOKINGS =====================

        case "list":

            List<Booking> list = dao.getAllBookings();

            out.print("[");

            for (int i = 0; i < list.size(); i++) {

                Booking b = list.get(i);

                out.print("{");

                out.print("\"bookingId\":" + b.getBookingId() + ",");
                out.print("\"passengerId\":" + b.getPassengerId() + ",");
                out.print("\"flightId\":" + b.getFlightId() + ",");
                out.print("\"passengerName\":\"" + b.getPassengerName() + "\",");
                out.print("\"flightNumber\":\"" + b.getFlightNumber() + "\",");
                out.print("\"bookingDate\":\"" + b.getBookingDate() + "\",");
                out.print("\"seatNumber\":\"" + b.getSeatNumber() + "\",");
                out.print("\"bookingStatus\":\"" + b.getBookingStatus() + "\"");

                out.print("}");

                if (i < list.size() - 1)
                    out.print(",");

            }

            out.print("]");

            break;

        //===================== GET BOOKING =====================

        case "get":

            int bookingId =
                    Integer.parseInt(request.getParameter("id"));

            Booking b =
                    dao.getBookingById(bookingId);

            if (b != null) {

                out.print("{");

                out.print("\"bookingId\":" + b.getBookingId() + ",");
                out.print("\"passengerId\":" + b.getPassengerId() + ",");
                out.print("\"flightId\":" + b.getFlightId() + ",");
                out.print("\"bookingDate\":\"" + b.getBookingDate() + "\",");
                out.print("\"seatNumber\":\"" + b.getSeatNumber() + "\",");
                out.print("\"bookingStatus\":\"" + b.getBookingStatus() + "\"");

                out.print("}");

            } else {

                out.print("{}");

            }

            break;
            //===================== DELETE BOOKING =====================

            case "delete":

                int id = Integer.parseInt(request.getParameter("id"));

                if (dao.deleteBooking(id))
                    out.print("Deleted Successfully");
                else
                    out.print("Delete Failed");

                break;

            //===================== PASSENGER DROPDOWN =====================

            case "passengers":

                List<Booking> passengers = dao.getAllPassengers();

                out.print("[");

                for (int i = 0; i < passengers.size(); i++) {

                    Booking p = passengers.get(i);

                    out.print("{");

                    out.print("\"passengerId\":" + p.getPassengerId() + ",");
                    out.print("\"passengerName\":\"" + p.getPassengerName() + "\"");

                    out.print("}");

                    if (i < passengers.size() - 1)
                        out.print(",");

                }

                out.print("]");

                break;

            //===================== FLIGHT DROPDOWN =====================

            case "flights":

                List<Booking> flights = dao.getAllFlights();

                out.print("[");

                for (int i = 0; i < flights.size(); i++) {

                    Booking f = flights.get(i);

                    out.print("{");

                    out.print("\"flightId\":" + f.getFlightId() + ",");
                    out.print("\"flightNumber\":\"" + f.getFlightNumber() + "\"");

                    out.print("}");

                    if (i < flights.size() - 1)
                        out.print(",");

                }

                out.print("]");

                break;

            }

        }
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            response.setContentType("text/plain");

            String action = request.getParameter("action");

            Booking booking = new Booking();

            booking.setPassengerId(
                    Integer.parseInt(request.getParameter("passengerId")));

            booking.setFlightId(
                    Integer.parseInt(request.getParameter("flightId")));

            booking.setBookingDate(
                    request.getParameter("bookingDate"));

            booking.setSeatNumber(
                    request.getParameter("seatNumber"));

            booking.setBookingStatus(
                    request.getParameter("bookingStatus"));

            // ================= ADD =================

            if ("add".equals(action)) {

                if (dao.addBooking(booking))

                    response.sendRedirect(
                            "UI/Bookings.html?msg=added");

                else

                    response.sendRedirect(
                            "UI/Bookings.html?msg=failed");

            }

            // ================= UPDATE =================

            else if ("update".equals(action)) {

                booking.setBookingId(
                        Integer.parseInt(
                                request.getParameter("bookingId")));

                if (dao.updateBooking(booking))

                    response.sendRedirect(
                            "UI/Bookings.html?msg=updated");

                else

                    response.sendRedirect(
                            "UI/Bookings.html?msg=failed");

            }

            // ================= DELETE =================

            else if ("delete".equals(action)) {

                int bookingId =
                        Integer.parseInt(
                                request.getParameter("bookingId"));

                if (dao.deleteBooking(bookingId))

                    response.sendRedirect(
                            "UI/Bookings.html?msg=deleted");

                else

                    response.sendRedirect(
                            "UI/Bookings.html?msg=failed");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}