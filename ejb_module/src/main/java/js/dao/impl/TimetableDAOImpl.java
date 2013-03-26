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
import js.exception.DataAccessException;
import js.exception.InvalidInputException;

/**
 * @author Alexander Markov
 */
@Stateless
public class TimetableDAOImpl implements TimetableDAO {

	@Override
	public Set<Timetable> getTimetableByStation(Stations station)
			throws DataAccessException, InvalidInputException {
		Set<Timetable> timetables = new HashSet<Timetable>();
		try {
			HibernateUtil.beginTransaction();
			Stations stationT = (Stations) HibernateUtil.getSession()
					.createCriteria(Stations.class)
					.add(Restrictions.eq("name", station.getName()))
					.uniqueResult();
			if (stationT == null) {
				throw new InvalidInputException("Station " + station.getName()
						+ " does not exist");
			}
			timetables = stationT.getTimetablesForDepartureStationId();
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DataAccessException(e.getLocalizedMessage());
		} finally {
			HibernateUtil.closeSession();
		}
		return timetables;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Timetable> getTimetableFromAToBInTimeInterval(
			Stations departureStation, Stations arrivalStation, Date timeFrom,
			Date timeTo) throws DataAccessException, InvalidInputException {
		List<Timetable> timetables = new ArrayList<Timetable>();
		try {
			HibernateUtil.beginTransaction();
			Stations stationA = (Stations) HibernateUtil.getSession()
					.createCriteria(Stations.class)
					.add(Restrictions.eq("name", departureStation.getName()))
					.uniqueResult();
			if (stationA == null) {
				throw new InvalidInputException("Station "
						+ departureStation.getName() + " does not exist");
			}
			Stations stationB = (Stations) HibernateUtil.getSession()
					.createCriteria(Stations.class)
					.add(Restrictions.eq("name", arrivalStation.getName()))
					.uniqueResult();
			if (stationB == null) {
				throw new InvalidInputException("Station "
						+ arrivalStation.getName() + " does not exist");
			}
			if (timeFrom.after(timeTo)) {
				throw new InvalidInputException("Incorrect time interval");
			}
			timetables = (List<Timetable>) HibernateUtil
					.getSession()
					.createCriteria(Timetable.class)
					.add(Restrictions.eq("stationsByDepartureStationId",
							stationA))
					.add(Restrictions
							.eq("stationsByArrivalStationId", stationB))
					.add(Restrictions
							.between("departureTime", timeFrom, timeTo)).list();
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DataAccessException(e.getLocalizedMessage());
		} finally {
			HibernateUtil.closeSession();
		}
		return timetables;
	}
}