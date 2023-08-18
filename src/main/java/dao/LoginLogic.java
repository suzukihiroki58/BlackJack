package dao;

import model.Account;
import model.Login;

public class LoginLogic {
	public boolean execute(Login login) {
		AccountsDAO dao = new AccountsDAO();
		Account account = dao.findByLogin(login);
		return account != null;
	}
}
