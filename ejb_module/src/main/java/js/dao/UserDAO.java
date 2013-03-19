package js.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import js.entity.Tickets;
import js.entity.Users;
import js.exception.UserAuthenticationFailedException;
import js.exception.UserRegistrationFailedException;

/**
 * @author Alexander Markov
 */
public interface UserDAO {
	/**
	 * @param login
	 * @param password
	 * @param email
	 * @param name
	 * @param surname
	 * @param birthDate
	 * @return New registered user instance
	 * @throws UserRegistrationFailedException
	 */
	public Users registerUser(String login, String password, String email,
			String name, String surname, Date birthDate)
			throws UserRegistrationFailedException;

	/**
	 * @param login
	 * @param password
	 * @return True if user with such login and password exists, otherwise false
	 * @throws UserAuthenticationFailedException
	 */
	public Boolean authenticateUser(String login, String password)
			throws UserAuthenticationFailedException;

	/**
	 * @return List of users or null
	 */
	public List<Users> getAllUsers();

	/**
	 * @param user
	 * @return Set of tickets
	 */
	public Set<Tickets> getTicketsByUser(Users user);
}