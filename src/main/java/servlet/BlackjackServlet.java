package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BlackjackGame;
import service.BlackjackGameFacade;

@WebServlet("/BlackjackServlet")
public class BlackjackServlet extends HttpServlet {
	private final BlackjackGameFacade facade = new BlackjackGameFacade();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		BlackjackGame game = facade.initializeGame();
		session.setAttribute("game", game);
		req.setAttribute("game", game);
		req.getRequestDispatcher("/WEB-INF/blackjack.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String handIndexStr = request.getParameter("handIndex");
		int handIndex = 0;
	    if (handIndexStr != null && !handIndexStr.isEmpty()) {
	        try {
	            handIndex = Integer.parseInt(handIndexStr);
	        } catch (NumberFormatException e) {
	        	e.printStackTrace();
	        }
	    }
	    String betAmountStr = request.getParameter("betAmount");
	    if ("bet".equals(action) && betAmountStr != null && !betAmountStr.isEmpty()) {
	        try {
	            int betAmount = Integer.parseInt(betAmountStr);
	            BlackjackGame game = facade.getOrCreateGame(session);
	            game.setBetAmount(betAmount);
	        } catch (NumberFormatException e) {
	        	e.printStackTrace();
	        }
	    }
		facade.handlePostRequest(session, action, handIndex);
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/blackjack.jsp");
		dispatcher.forward(request, response);

	}
}
