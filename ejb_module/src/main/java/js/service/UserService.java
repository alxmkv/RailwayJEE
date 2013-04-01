package js.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;

import js.dao.impl.UserDAOImpl;
import js.dto.TicketDTO;
import js.dto.UserDTO;
import js.entity.Tickets;
import js.entity.Timetable;
import js.entity.Trains;
import js.entity.Users;
import js.exception.DataAccessException;
import js.exception.UserRegistrationFailedException;

/**
 * @author Alexander Markov
 */
@Stateless
public class UserService {

	private UserDAOImpl userDAOImpl = new UserDAOImpl();

	/**
	 * @param login
	 * @param password
	 * @return user type if user with such login and password exists (2 for
	 *         simple passenger, 1 for admin), <code>-1</code> otherwise
	 * @throws DataAccessException
	 */
	public int authenticateUser(String login, String password)
			throws DataAccessException {
		return userDAOImpl.authenticateUser(login, password);
	}

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
			throws UserRegistrationFailedException, DataAccessException {
		return userDAOImpl.registerUser(login, password, email, name, surname,
				birthDate);
	}

	/**
	 * @return Map [login - name, surname, birthdate, email, type, status]
	 * @throws DataAccessException
	 */
	public Map<String, UserDTO> getAllUsers() throws DataAccessException {
		List<Users> users = userDAOImpl.getAllUsers();
		Map<String, UserDTO> result = new HashMap<String, UserDTO>();
		Iterator<Users> iter = users.iterator();
		while (iter.hasNext()) {
			Users user = iter.next();
			result.put(
					user.getLogin(),
					new UserDTO(user.getName(), user.getSurname(), user
							.getBirthdate(), user.getEmail(), user
							.getUserType(), user.getStatus()));
		}
		return result;
	}

	/**
	 * @param userName
	 * @return Map [ticket number - train number, train name, departure station,
	 *         arrival station, date, departure time, arrival time]
	 * @throws DataAccessException
	 */
	public Map<Long, TicketDTO> getTicketsByUser(String userName)
			throws DataAccessException {
		Users user = new Users();
		user.setLogin(userName);
		Set<Tickets> tickets = userDAOImpl.getTicketsByUser(user);
		Map<Long, TicketDTO> result = new HashMap<Long, TicketDTO>();
		Iterator<Tickets> iter = tickets.iterator();
		while (iter.hasNext()) {
			Tickets ticket = iter.next();
			Timetable timetable = ticket.getTimetable();
			Trains train = timetable.getTrains();
			result.put(ticket.getId(), new TicketDTO(ticket.getDate(),
					timetable.getStationsByDepartureStationId().getName(),
					timetable.getStationsByArrivalStationId().getName(),
					timetable.getDepartureTime(), timetable.getArrivalTime(),
					train.getNumber(), train.getName()));
		}
		return result;
	}
}