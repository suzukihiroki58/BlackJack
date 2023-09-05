package service;

import dao.UsersDAO;
import model.Account;
import model.UserCredential;

public class LoginLogic {
	public boolean execute(UserCredential login) {
		UsersDAO dao = new UsersDAO();
		Account account = dao.findAccountByUserNameAndPassword(login);
		return account != null;
	}
}
