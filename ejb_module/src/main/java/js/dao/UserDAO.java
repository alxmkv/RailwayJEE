package js.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import js.entity.Tickets;
import js.entity.Users;
import js.exception.DataAccessException;
import js.exception.InvalidInputException;
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
	 * @return user type if user with such login and password exists (2 for
	 *         simple passenger, 1 for admin), <code>-1</code> otherwise
	 * @throws DataAccessException
	 */
	public int authenticateUser(String login, String password)
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

	/**
	 * @param login
	 * @param userType
	 *            2 for simple passenger, 1 for admin
	 * @return <code>true</code> if user access rights are successfully changed,
	 *         <code>false</code> otherwise
	 * @throws DataAccessException
	 * @throws InvalidInputException
	 */
	public Boolean setAccessRights(String login, Byte userType)
			throws DataAccessException, InvalidInputException;
}