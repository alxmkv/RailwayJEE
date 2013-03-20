package js.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import js.dao.impl.UserDAOImpl;
import js.entity.Tickets;
import js.entity.Users;
import js.exception.UserAuthenticationFailedException;
import js.exception.UserRegistrationFailedException;

@Stateless
public class UserService {

	@EJB
	private UserDAOImpl userDAOImpl = new UserDAOImpl();

	public Boolean authenticateUser(String login, String password)
			throws UserAuthenticationFailedException {
		return userDAOImpl.authenticateUser(login, password);
	}

	public Users registerUser(String login, String password, String email,
			String name, String surname, Date birthDate)
			throws UserRegistrationFailedException {
		return userDAOImpl.registerUser(login, password, email, name, surname,
				birthDate);
	}

	public List<Users> getAllUsers() {
		return userDAOImpl.getAllUsers();
	}

	public Set<Tickets> getTicketsByUser(Users user) {
		return userDAOImpl.getTicketsByUser(user);
	}
}