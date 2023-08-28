package service;

import dao.AccountsDAO;
import model.Account;
import model.UserCredential;

public class LoginLogic {
	public boolean execute(UserCredential login) {
		AccountsDAO dao = new AccountsDAO();
		Account account = dao.findAccountByUserNameAndPassword(login);
		return account != null;
	}
}
