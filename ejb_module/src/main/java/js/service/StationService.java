package js.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;

import js.dao.impl.StationDAOImpl;
import js.entity.Stations;
import js.exception.DataAccessException;

/**
 * @author Alexander Markov
 */
@Stateless
public class StationService {

	private StationDAOImpl stationDAOImpl = new StationDAOImpl();

	public Boolean addStation(String name) throws DataAccessException {
		Stations station = new Stations(name);
		return stationDAOImpl.addStation(station);
	}

	public Set<String> getAllStations() throws DataAccessException {
		List<Stations> stations = stationDAOImpl.getAllStations();
		Set<String> result = new HashSet<String>();
		Iterator<Stations> iter = stations.iterator();
		while (iter.hasNext()) {
			Stations station = iter.next();
			result.add(station.getName());
		}
		return result;
	}
}