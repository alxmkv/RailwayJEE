package js.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import js.entity.Tickets;
import js.entity.Users;
import js.exception.DataAccessException;
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
	 * @return <code>true</code> if user is successfully registered,
	 *         <code>false</code> otherwise
	 * @throws UserRegistrationFailedException
	 * @throws DataAccessException
	 */
	public Boolean registerUser(String login, String password, String email,
			String name, String surname, Date birthDate)
			throws UserRegistrationFailedException, DataAccessException;

	/**
	 * @param login
	 * @param password
	 * @return <code>true</code> if user with such login and password exists,
	 *         <code>false</code> otherwise
	 * @throws DataAccessException
	 */
	public Boolean authenticateUser(String login, String password)
			throws DataAccessException;

	/**
	 * @return List of users
	 * @throws DataAccessException
	 */
	public List<Users> getAllUsers() throws DataAccessException;

	/**
	 * @param user
	 * @return Set of tickets
	 * @throws DataAccessException
	 */
	public Set<Tickets> getTicketsByUser(Users user) throws DataAccessException;
}