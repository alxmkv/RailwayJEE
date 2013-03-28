package js.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;

import js.dao.impl.UserDAOImpl;
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
	public Map<String, List<?>> getAllUsers() throws DataAccessException {
		List<Users> users = userDAOImpl.getAllUsers();
		Map<String, List<?>> result = new HashMap<String, List<?>>();
		Iterator<Users> iter = users.iterator();
		while (iter.hasNext()) {
			Users user = iter.next();
			List<Object> list = new ArrayList<Object>();
			list.add(user.getName());
			list.add(user.getSurname());
			list.add(user.getBirthdate());
			list.add(user.getEmail());
			list.add(user.getUserType());
			list.add(user.getStatus());
			result.put(user.getLogin(), list);
		}
		return result;
	}

	/**
	 * @param userName
	 * @return Map [ticket number - train number, train name, departure station,
	 *         arrival station, date, departure time, arrival time]
	 * @throws DataAccessException
	 */
	public Map<Long, List<?>> getTicketsByUser(String userName)
			throws DataAccessException {
		Users user = new Users();
		user.setLogin(userName);
		Set<Tickets> tickets = userDAOImpl.getTicketsByUser(user);
		Map<Long, List<?>> result = new HashMap<Long, List<?>>();
		Iterator<Tickets> iter = tickets.iterator();
		while (iter.hasNext()) {
			Tickets ticket = iter.next();
			Timetable timetable = ticket.getTimetable();
			Trains train = timetable.getTrains();
			List<Object> list = new ArrayList<Object>();
			list.add(train.getNumber());
			list.add(train.getName());
			list.add(timetable.getStationsByDepartureStationId().getName());
			list.add(timetable.getStationsByArrivalStationId().getName());
			list.add(ticket.getDate());
			list.add(timetable.getDepartureTime());
			list.add(timetable.getArrivalTime());
			result.put(ticket.getId(), list);
		}
		return result;
	}
}