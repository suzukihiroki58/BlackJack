package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountsDAO;
import model.Account;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AccountsDAO dao = new AccountsDAO();
		List<Account> allUsers = dao.getAllUsers();

		request.setAttribute("users", allUsers);
		request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
	}
}
