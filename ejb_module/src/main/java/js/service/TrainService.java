package js.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;

import js.dao.impl.TrainDAOImpl;
import js.dto.TrainServiceDTO;
import js.dto.UserDTO;
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
	public Map<Integer, TrainServiceDTO> getAllTrains()
			throws DataAccessException {
		List<Trains> trains = trainDAOImpl.getAllTrains();
		Map<Integer, TrainServiceDTO> result = new HashMap<Integer, TrainServiceDTO>();
		Iterator<Trains> trainIterator = trains.iterator();
		while (trainIterator.hasNext()) {
			Trains train = trainIterator.next();
			Set<Timetable> timetables = train.getTimetables();
			Iterator<Timetable> timetableIterator = timetables.iterator();
			if (timetableIterator.hasNext()) {
				Timetable timetable = timetableIterator.next();
				result.put(
						train.getNumber(),
						new TrainServiceDTO(train.getName(), timetable
								.getStationsByDepartureStationId().getName(),
								timetable.getStationsByArrivalStationId()
										.getName(), timetable
										.getDepartureTime(), timetable
										.getArrivalTime(), train.getCapacity()));
			} else {
				result.put(train.getNumber(),
						new TrainServiceDTO(train.getName(), "-", "-", null,
								null, train.getCapacity()));
			}
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
	 * @throws InvalidInputException
	 */
	public Boolean addTrain(int number, String name, int capacity,
			String departureStationName, String arrivalStationName,
			Date departureTime, Date arrivalTime) throws DataAccessException,
			InvalidInputException {
		Trains train = new Trains(number, name, capacity);
		return trainDAOImpl.addTrain(train, departureStationName,
				arrivalStationName, departureTime, arrivalTime);
	}

	/**
	 * @param trainNumber
	 * @param date
	 * @return Map [login - name, surname, birthdate, email, type, status]
	 * @throws DataAccessException
	 * @throws InvalidInputException
	 */
	public Map<String, UserDTO> getUsersByTrain(Integer trainNumber, Date date)
			throws DataAccessException, InvalidInputException {
		List<Users> users = trainDAOImpl.getUsersByTrain(trainNumber, date);
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
}