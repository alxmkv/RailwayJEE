package js.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;

import js.dao.impl.TimetableDAOImpl;
import js.dto.TimetableServiceDTO;
import js.entity.Stations;
import js.entity.Timetable;
import js.entity.Trains;
import js.exception.DataAccessException;
import js.exception.InvalidInputException;

/**
 * @author Alexander Markov
 */
@Stateless
public class TimetableService {

	private TimetableDAOImpl timetableDAOImpl = new TimetableDAOImpl();

	/**
	 * @param stationName
	 * @return Map [train number - train name, arrival station, departure time,
	 *         arrival time, tickets left]
	 * @throws DataAccessException
	 * @throws InvalidInputException
	 */
	public Map<String, TimetableServiceDTO> getTimetableByStation(
			String stationName) throws DataAccessException,
			InvalidInputException {
		Set<Timetable> timetables = timetableDAOImpl
				.getTimetableByStation(new Stations(stationName));
		Map<String, TimetableServiceDTO> result = new HashMap<String, TimetableServiceDTO>();
		Iterator<Timetable> iter = timetables.iterator();
		while (iter.hasNext()) {
			Timetable timetable = iter.next();
			Trains train = timetable.getTrains();
			result.put(
					new Integer(train.getNumber()).toString(),
					new TimetableServiceDTO(train.getName(), timetable
							.getStationsByArrivalStationId().getName(),
							timetable.getDepartureTime(), timetable
									.getArrivalTime(), train.getCapacity()
									- timetable.getTickets().size()));
		}
		return result;
	}

	/**
	 * @param departureStationName
	 * @param arrivalStationName
	 * @param timeFrom
	 * @param timeTo
	 * @return Map [train number - train name, departure time, arrival time,
	 *         tickets left]
	 * @throws DataAccessException
	 * @throws InvalidInputException
	 */
	public Map<Integer, TimetableServiceDTO> getTimetableFromAToBInTimeInterval(
			String departureStationName, String arrivalStationName,
			Date timeFrom, Date timeTo) throws DataAccessException,
			InvalidInputException {
		List<Timetable> timetables = timetableDAOImpl
				.getTimetableFromAToBInTimeInterval(new Stations(
						departureStationName),
						new Stations(arrivalStationName), timeFrom, timeTo);
		Map<Integer, TimetableServiceDTO> result = new HashMap<Integer, TimetableServiceDTO>();
		Iterator<Timetable> iter = timetables.iterator();
		while (iter.hasNext()) {
			Timetable timetable = iter.next();
			Trains train = timetable.getTrains();
			result.put(
					train.getNumber(),
					new TimetableServiceDTO(train.getName(), timetable
							.getStationsByArrivalStationId().getName(),
							timetable.getDepartureTime(), timetable
									.getArrivalTime(), train.getCapacity()
									- timetable.getTickets().size()));
		}
		return result;
	}
}