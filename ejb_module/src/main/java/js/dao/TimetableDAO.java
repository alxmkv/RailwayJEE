package js.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import js.entity.Stations;
import js.entity.Timetable;

/**
 * @author Alexander Markov
 */
public interface TimetableDAO {
	/**
	 * @param stationA
	 * @param stationB
	 * @param timeFrom
	 * @param timeTo
	 * @return List of trains going from station A to station B in a fixed time
	 *         interval
	 */
	public List<?> getTrainsFromAToBInTimeInterval(Stations stationA,
			Stations stationB, Date timeFrom, Date timeTo);

	/**
	 * @param station
	 * @return List of trains for a fixed station
	 */
	public Set<Timetable> getTimetableByStation(Stations station);
}