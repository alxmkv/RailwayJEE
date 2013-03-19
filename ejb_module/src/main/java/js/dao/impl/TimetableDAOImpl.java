package js.dao.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import js.dao.HibernateUtil;
import js.dao.TimetableDAO;
import js.entity.Stations;
import js.entity.Timetable;

/**
 * Session Bean implementation class TimetableDAOImpl
 * 
 * @author Alexander Markov
 */
@Stateless
public class TimetableDAOImpl implements TimetableDAO {

	@Override
	public Set<Timetable> getTimetableByStation(Stations station) {
		Set<Timetable> timetables = new HashSet<Timetable>();
		try {
			HibernateUtil.beginTransaction();
			Stations stationT = (Stations) HibernateUtil.getSession()
					.createCriteria(Stations.class)
					.add(Restrictions.eq("name", station.getName()))
					.uniqueResult();
			timetables= stationT.getTimetablesForDepartureStationId();
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			return null;
		} finally {
			HibernateUtil.closeSession();
		}
		return timetables;
	}

	@Override
	public List<?> getTrainsFromAToBInTimeInterval(Stations stationA,
			Stations stationB, Date timeFrom, Date timeTo) {
		return null;
	}
}