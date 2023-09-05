package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import service.AdminFacade;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final AdminFacade adminFacade = new AdminFacade();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Account> allUsers = adminFacade.fetchAllUsers();
		request.setAttribute("users", allUsers);
		request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
	}
}
