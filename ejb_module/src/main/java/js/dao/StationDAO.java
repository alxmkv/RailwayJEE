package js.dao;

import java.util.List;

import js.entity.Stations;
import js.exception.DataAccessException;

/**
 * @author Alexander Markov
 */
public interface StationDAO {
	/**
	 * @param station
	 * @return <code>true</code> if a station is successfully added
	 * @throws DataAccessException
	 */
	public Boolean addStation(Stations station) throws DataAccessException;

	/**
	 * @return List of stations
	 * @throws DataAccessException
	 */
	public List<Stations> getAllStations() throws DataAccessException;
}