package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.GameRecord;
import service.RankingFacade;

@WebServlet("/RankingServlet")
public class RankingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final RankingFacade rankingFacade = new RankingFacade();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<GameRecord> records = rankingFacade.fetchAllRankings();

		request.setAttribute("rankings", records);
		request.getRequestDispatcher("/WEB-INF/ranking.jsp").forward(request, response);
	}
}
