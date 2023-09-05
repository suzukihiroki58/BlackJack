package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserCredential;
import service.UserFacade;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		UserCredential userCredential = new UserCredential(userName, password);
		UserFacade facade = new UserFacade();
		boolean result = facade.authenticate(userCredential);

		if (result) {
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", userCredential);

			session.setAttribute("userId", userCredential.getUserId());

			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/loginOK.jsp");
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect("WelcomeServlet");
		}
	}
}
