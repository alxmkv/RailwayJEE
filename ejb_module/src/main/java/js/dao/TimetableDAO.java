package js.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import js.entity.Stations;
import js.entity.Timetable;
import js.exception.DataAccessException;
import js.exception.InvalidInputException;

/**
 * @author Alexander Markov
 */
public interface TimetableDAO {
	/**
	 * @param departureStation
	 * @param arrivalStation
	 * @param timeFrom
	 * @param timeTo
	 * @return List of trains going from station A to station B in a fixed time
	 *         interval
	 * @throws DataAccessException
	 * @throws InvalidInputException
	 */
	public List<Timetable> getTimetableFromAToBInTimeInterval(
			Stations departureStation, Stations arrivalStation, Date timeFrom,
			Date timeTo) throws DataAccessException, InvalidInputException;

	/**
	 * @param station
	 * @return List of trains for a fixed station
	 * @throws DataAccessException
	 * @throws InvalidInputException
	 */
	public Set<Timetable> getTimetableByStation(Stations station)
			throws DataAccessException, InvalidInputException;
}