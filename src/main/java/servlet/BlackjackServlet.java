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

@WebServlet("/BlackjackServlet")
public class BlackjackServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		BlackjackGame game = new BlackjackGame();
		session.setAttribute("game", game);
		req.setAttribute("game", game);
		req.getRequestDispatcher("/WEB-INF/blackjack.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		BlackjackGame game = (BlackjackGame) session.getAttribute("game");

		if (game == null) {
			game = new BlackjackGame();
			session.setAttribute("game", game);
		} else {
			String action = request.getParameter("action");
			if ("hit".equals(action)) {
				game.playerHit();
			} else if ("stand".equals(action)) {
				game.playerStand();
				boolean shouldDealerStand = false;
				if (game.getDealer().getHand().size() == 2 && game.getDealer().getHandTotal() >= 17) {
					shouldDealerStand = true;
				}
				if (!shouldDealerStand) {
					while (game.getDealer().getHandTotal() < 17) {
						if (game.getDealer().getHandTotal() > 21) {
							break;
						}
						game.getDealer().receiveCard(game.drawCard());
					}
				}
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/blackjack.jsp");
		dispatcher.forward(request, response);
	}
}
