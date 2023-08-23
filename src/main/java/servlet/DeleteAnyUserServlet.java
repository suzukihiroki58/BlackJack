package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountsDAO;

@WebServlet("/DeleteAnyUserServlet")
public class DeleteAnyUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        
        AccountsDAO dao = new AccountsDAO();
        dao.deleteGameRecords(userId);
        dao.deleteUser(userId); 
        
        response.sendRedirect("AdminServlet"); 
    }
}
