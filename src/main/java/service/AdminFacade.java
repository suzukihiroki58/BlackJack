package service;

import java.util.List;

import dao.UsersDAO;
import model.Account;

public class AdminFacade {
	
	private UsersDAO usersDAO;

    public AdminFacade() {
        this.usersDAO = new UsersDAO();
    }

    public List<Account> fetchAllUsers() {
        return usersDAO.getAllUsers();
    }

}
