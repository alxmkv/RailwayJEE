package js.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;

import js.dao.impl.TrainDAOImpl;
import js.entity.Timetable;
import js.entity.Trains;
import js.entity.Users;
import js.exception.DataAccessException;
import js.exception.InvalidInputException;

/**
 * @author Alexander Markov
 */
@Stateless
public class TrainService {

	private TrainDAOImpl trainDAOImpl = new TrainDAOImpl();

	/**
	 * @return Map [train number - train name, departure station, arrival
	 *         station, departure time, arrival time, capacity]
	 * @throws DataAccessException
	 */
	public Map<Integer, List<?>> getAllTrains() throws DataAccessException {
		List<Trains> trains = trainDAOImpl.getAllTrains();
		Map<Integer, List<?>> result = new HashMap<Integer, List<?>>();
		Iterator<Trains> trainIterator = trains.iterator();
		while (trainIterator.hasNext()) {
			Trains train = trainIterator.next();
			Set<Timetable> timetables = train.getTimetables();
			Iterator<Timetable> timetableIterator = timetables.iterator();
			List<Object> list = new ArrayList<Object>();
			if (timetableIterator.hasNext()) {
				Timetable timetable = timetableIterator.next();
				list.add(train.getName());
				list.add(timetable.getStationsByDepartureStationId().getName());
				list.add(timetable.getStationsByArrivalStationId().getName());
				list.add(timetable.getDepartureTime());
				list.add(timetable.getArrivalTime());
				list.add(train.getCapacity());
			} else {
				list.add(train.getName());
				list.add("-");
				list.add("-");
				list.add("-");
				list.add("-");
				list.add(train.getCapacity());
			}
			result.put(train.getNumber(), list);
		}
		return result;
	}

	/**
	 * @param number
	 * @param name
	 * @param capacity
	 * @param departureStationName
	 * @param arrivalStationName
	 * @param departureTime
	 * @param arrivalTime
	 * @return <code>true</code> if a train is successfully added
	 * @throws DataAccessException
	 */
	public Boolean addTrain(int number, String name, int capacity,
			String departureStationName, String arrivalStationName,
			Date departureTime, Date arrivalTime) throws DataAccessException {
		Trains train = new Trains(number, name, capacity);
		return trainDAOImpl.addTrain(train, departureStationName,
				arrivalStationName, departureTime, arrivalTime);
	}

	/**
	 * @param name
	 * @return Map [login - name, surname, birthdate, email, type, status]
	 * @throws DataAccessException
	 * @throws InvalidInputException
	 */
	public Map<String, List<?>> getUsersByTrain(String name)
			throws DataAccessException, InvalidInputException {
		List<Users> users = trainDAOImpl.getUsersByTrain(name);
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
}