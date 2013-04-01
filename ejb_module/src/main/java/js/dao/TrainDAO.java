package js.dao;

import java.util.Date;
import java.util.List;

import js.entity.Trains;
import js.entity.Users;
import js.exception.DataAccessException;
import js.exception.InvalidInputException;

/**
 * @author Alexander Markov
 */
public interface TrainDAO {
	/**
	 * @return List of trains or null
	 * @throws DataAccessException
	 */
	public List<Trains> getAllTrains() throws DataAccessException;

	/**
	 * @param train
	 * @param departureStationName
	 * @param arrivalStationName
	 * @param departureTime
	 * @param arrivalTime
	 * @return <code>true</code> if a train is successfully added
	 * @throws DataAccessException
	 * @throws InvalidInputException
	 */
	public Boolean addTrain(Trains train, String departureStationName,
			String arrivalStationName, Date departureTime, Date arrivalTime)
			throws DataAccessException, InvalidInputException;

	/**
	 * @param trainNumber
	 * @param date
	 * @return List of passengers for this train and date
	 * @throws DataAccessException
	 * @throws InvalidInputException
	 */
	public List<Users> getUsersByTrain(Integer trainNumber, Date date)
			throws DataAccessException, InvalidInputException;
}