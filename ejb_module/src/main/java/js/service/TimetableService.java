package js.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import js.dao.impl.TimetableDAOImpl;
import js.entity.Stations;
import js.entity.Timetable;
import js.entity.Trains;

/**
 * @author Alexander Markov
 */
@Stateless
public class TimetableService {

	@EJB
	private TimetableDAOImpl timetableDAOImpl = new TimetableDAOImpl();

	public Map<String, List<?>> getTimetableByStation(String stationName) {
		Set<Timetable> timetables = timetableDAOImpl
				.getTimetableByStation(new Stations(stationName));
		System.out.println("Timetables number = " + timetables.size());
		Map<String, List<?>> result = new HashMap<String, List<?>>();
		Iterator<Timetable> iter = timetables.iterator();
		while (iter.hasNext()) {
			Timetable timetable = iter.next();
			List<Object> list = new ArrayList<Object>();
			Trains train = timetable.getTrains();
			list.add(train.getNumber());
			list.add(timetable.getStationsByArrivalStationId().getName());
			list.add(timetable.getDepartureTime());
			list.add(timetable.getArrivalTime());
			list.add(train.getCapacity());
			result.put(train.getName(), list);
		}
		return result;
	}

	public Map<String, List<?>> getTimetableFromAToBInTimeInterval(
			String stationAName, String stationBName, Date timeFrom, Date timeTo) {
		List<Timetable> timetables = timetableDAOImpl
				.getTimetableFromAToBInTimeInterval(new Stations(stationAName),
						new Stations(stationBName), timeFrom, timeTo);
		Map<String, List<?>> result = new HashMap<String, List<?>>();
		Iterator<Timetable> iter = timetables.iterator();
		while (iter.hasNext()) {
			Timetable timetable = iter.next();
			List<Object> list = new ArrayList<Object>();
			Trains train = timetable.getTrains();
			list.add(train.getNumber());
			list.add(timetable.getStationsByArrivalStationId().getName());
			list.add(timetable.getDepartureTime());
			list.add(timetable.getArrivalTime());
			list.add(train.getCapacity());
			result.put(train.getName(), list);
		}
		return result;
	}
}