package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BlackjackGame;
import service.UserFacade;

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
		UserFacade userFacade = new UserFacade();
		boolean success = userFacade.deleteUserIfLoggedIn(session);

		if (success) {
			response.sendRedirect("WelcomeServlet?message=UserDeleted");
		} else {
			response.sendRedirect("DeleteUserServlet");
		}

	}
}
