package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountsDAO;
import model.GameRecord;

@WebServlet("/MyRecordServlet")
public class MyRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MyRecordServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userId = (String) request.getSession().getAttribute("userId");

		System.out.println("DEBUG: userId = " + userId);

		AccountsDAO dao = new AccountsDAO();

		GameRecord record = dao.getUserRecords(userId);

		if (record != null) {
			request.setAttribute("totalGames", record.getTotalGames());
			request.setAttribute("wins", record.getWins());
			request.setAttribute("losses", record.getLosses());
			request.setAttribute("draws", record.getDraws());
			request.setAttribute("winRate", record.getWinrate());

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/myrecord.jsp");
			dispatcher.forward(request, response);
		} else {
			request.setAttribute("errorMessage", "個人戦績の取得に失敗しました。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/myrecord.jsp");
			dispatcher.forward(request, response);
		}
	}
}
