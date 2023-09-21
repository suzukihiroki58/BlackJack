package service;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UsersDAO;
import model.Account;
import model.UserCredential;

public class UserFacade {
	
	private UsersDAO usersDAO;

    public UserFacade() {
        this.usersDAO = new UsersDAO();
    }
	
	public void deleteUser(String userId) {
        usersDAO.deleteUser(userId);
    }
	
	public boolean deleteUserIfLoggedIn(HttpSession session) {
        if (session != null) {
            UserCredential loggedInUser = (UserCredential) session.getAttribute("loginUser");
            if (loggedInUser != null) {
                deleteUser(loggedInUser.getUserName());
                session.invalidate();
                return true;
            }
        }
        return false;
    }
	
	public boolean authenticate(UserCredential login) {
        UsersDAO dao = new UsersDAO();
        Account account = dao.findAccountByUserNameAndPassword(login);
        return account != null;
    }
	
	public Optional<Account> getAccountByUserNameAndPassword(UserCredential credential) {
        return Optional.ofNullable(usersDAO.findAccountByUserNameAndPassword(credential));
    }
	
	public boolean loginUser(UserCredential credential, HttpSession session) {
        Optional<Account> account = getAccountByUserNameAndPassword(credential);
        if (account.isPresent()) {
            session.setAttribute("loginUser", account.get());
            return true;
        }
        return false;
    }
	
	
	private UsersDAO accountsDAO = new UsersDAO();
	
	public boolean registerUser(String userName, String password, String nickname) {
        boolean userNameExists = accountsDAO.isUserNameExists(userName);

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{10,}$";
        if (!password.matches(regex)) {
            return false;
        }

        if (userNameExists) {
            return false;
        }
        
        Account newAccount = new Account(userName, password, Optional.ofNullable(nickname).orElse(""));
        return accountsDAO.isUserRegisteredSuccessfully(newAccount);
    }
	
	public void handleRegistration(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    String userName = request.getParameter("userName");
	    String password = request.getParameter("password");
	    String nickname = request.getParameter("nickname");

	    boolean success = registerUser(userName, password, nickname);

	    if (success) {
	    	HttpSession session = request.getSession();
	        session.setAttribute("registrationMessage", "ユーザー登録が完了しました");
	        session.setAttribute("userName", userName);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
	        dispatcher.forward(request, response);
	    } else {
	        request.setAttribute("errorMessage", "ユーザー名が既に存在するか、パスワードが要件を満たしていません。");
	        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/register.jsp");
	        dispatcher.forward(request, response);
	    }
	}

}
