package js.dao;

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
}