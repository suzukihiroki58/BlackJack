package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UsersDAO;
import model.BlackjackGame;
import model.UserCredential;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlackjackGame game = new BlackjackGame();
		req.setAttribute("game", game);
		req.getRequestDispatcher("/WEB-INF/deleteuser.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			UserCredential loggedInUser = (UserCredential) session.getAttribute("loginUser");

			if (loggedInUser != null) {
				UsersDAO dbManager = new UsersDAO();
				dbManager.deleteUser(loggedInUser.getUserName());

				session.invalidate();
				response.sendRedirect("WelcomeServlet?message=UserDeleted");
				return;
			}
		}
		response.sendRedirect("DeleteUserServlet");
	}
}
