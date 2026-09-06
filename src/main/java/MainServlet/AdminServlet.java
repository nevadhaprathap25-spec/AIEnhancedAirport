package MainServlet;

import java.io.IOException;

import DAO.AdminDAO;
import Model.Login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/adminLogin")
public class AdminServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request,
	        HttpServletResponse response)
	        throws ServletException, IOException {

	    String action = request.getParameter("action");

	    if("dashboard".equals(action)){

	        response.setContentType("application/json");

	        AdminDAO dao = new AdminDAO();

	        response.getWriter().print(dao.getDashboardData());

	    }

	}

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username =
                request.getParameter("username");

        String password =
                request.getParameter("password");

        Login login = new Login();

        login.setUsername(username);
        login.setPassword(password);

        AdminDAO dao = new AdminDAO();

        String user = dao.login(login);

        if(user.equals("ADMIN")) {

            response.sendRedirect("UI/AdminDashboard.html");

        }
        else {

            response.getWriter().println(
                    "<h2>Invalid Username or Password</h2>");

        }

    }

}