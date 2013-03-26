package js.service;

import javax.ejb.Stateless;

import js.dao.impl.StationDAOImpl;
import js.entity.Stations;
import js.exception.DataAccessException;

/**
 * @author Alexander Markov
 */
@Stateless
// @LocalBean
public class StationService {

	private StationDAOImpl stationDAOImpl = new StationDAOImpl();

	public Boolean addStation(String name) throws DataAccessException {
		Stations station = new Stations(name);
		return stationDAOImpl.addStation(station);
	}
}