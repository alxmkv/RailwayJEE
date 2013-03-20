package js.dao.impl;

import java.util.ArrayList;
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
			timetables = stationT.getTimetablesForDepartureStationId();
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			return null;
		} finally {
			HibernateUtil.closeSession();
		}
		return timetables;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Timetable> getTimetableFromAToBInTimeInterval(
			Stations stationA, Stations stationB, Date timeFrom, Date timeTo) {
		List<Timetable> timetables = new ArrayList<Timetable>();
		try {
			HibernateUtil.beginTransaction();
			Stations stationAT = (Stations) HibernateUtil.getSession()
					.createCriteria(Stations.class)
					.add(Restrictions.eq("name", stationA.getName()))
					.uniqueResult();
			Stations stationBT = (Stations) HibernateUtil.getSession()
					.createCriteria(Stations.class)
					.add(Restrictions.eq("name", stationB.getName()))
					.uniqueResult();
			timetables = (List<Timetable>) HibernateUtil
					.getSession()
					.createCriteria(Timetable.class)
					.add(Restrictions.eq("stationsByDepartureStationId",
							stationAT))
					.add(Restrictions.eq("stationsByArrivalStationId",
							stationBT))
					.add(Restrictions
							.between("departureTime", timeFrom, timeTo)).list();
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			return null;
		} finally {
			HibernateUtil.closeSession();
		}
		return timetables;
	}
}