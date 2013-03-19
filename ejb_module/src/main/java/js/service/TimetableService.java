package js.service;

import java.util.ArrayList;
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

@Stateless
public class TimetableService {
	@EJB
	private TimetableDAOImpl timetableDAOImpl = new TimetableDAOImpl();
	
	public Map<String, List<?>> getTimetableByStation(Stations station) {
		Set<Timetable> timetables = timetableDAOImpl.getTimetableByStation(station);
		Map<String,List<?>> result = new HashMap<String, List<?>>();
		Iterator<Timetable> iter = timetables.iterator();
		while(iter.hasNext()) {
			Timetable timetable = iter.next();
			List<Object> list = new ArrayList<Object>();
			Trains train = timetable.getTrains();
			list.add(timetable.getDepartureTime());
			list.add(timetable.getArrivalTime());
			list.add(train.getNumber());
			list.add(train.getName());
			list.add(train.getCapacity());
			result.put(timetable.getStationsByArrivalStationId().getName(), list);
		}
		return result;
	}
}