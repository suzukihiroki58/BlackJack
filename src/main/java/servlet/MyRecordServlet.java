package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.RankingFacade;

@WebServlet("/MyRecordServlet")
public class MyRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final RankingFacade rankingFacade = new RankingFacade();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = (String) request.getSession().getAttribute("userId");
		rankingFacade.handleUserRecords(request, userId);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/myrecord.jsp");
		dispatcher.forward(request, response);
	}
}
