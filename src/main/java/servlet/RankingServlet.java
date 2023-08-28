package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountsDAO;
import model.GameRecord;

@WebServlet("/RankingServlet")
public class RankingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AccountsDAO dao = new AccountsDAO();
		List<GameRecord> records = dao.getAllUserRecords();

		request.setAttribute("rankings", records);
		request.getRequestDispatcher("/WEB-INF/ranking.jsp").forward(request, response);
	}
}
