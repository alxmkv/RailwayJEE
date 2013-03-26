/**
 * 
 */
package js.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import js.dao.HibernateUtil;
import js.dao.TrainDAO;
import js.entity.Stations;
import js.entity.Tickets;
import js.entity.Timetable;
import js.entity.Trains;
import js.entity.Users;
import js.exception.DataAccessException;
import js.exception.InvalidInputException;

/**
 * @author Alexander Markov
 */
@Stateless
public class TrainDAOImpl implements TrainDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Trains> getAllTrains() throws DataAccessException {
		List<Trains> trains = new ArrayList<Trains>();
		try {
			HibernateUtil.beginTransaction();
			trains = (List<Trains>) HibernateUtil.getSession()
					.createCriteria(Trains.class).addOrder(Order.asc("number"))
					.list();
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DataAccessException(e.getLocalizedMessage());
		} finally {
			HibernateUtil.closeSession();
		}
		return trains;
	}

	@Override
	public Boolean addTrain(Trains train, String departureStationName,
			String arrivalStationName, Date departureTime, Date arrivalTime)
			throws DataAccessException {
		try {
			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().saveOrUpdate("Trains", train);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DataAccessException(e.getLocalizedMessage());
		} finally {
			HibernateUtil.closeSession();
		}
		try {
			HibernateUtil.beginTransaction();
			Stations departureStation = (Stations) HibernateUtil.getSession()
					.createCriteria(Stations.class)
					.add(Restrictions.eq("name", departureStationName))
					.uniqueResult();
			Stations arrivalStation = (Stations) HibernateUtil.getSession()
					.createCriteria(Stations.class)
					.add(Restrictions.eq("name", arrivalStationName))
					.uniqueResult();
			Timetable timetable = new Timetable(arrivalStation,
					departureStation, train, departureTime, arrivalTime);
			HibernateUtil.getSession().saveOrUpdate("Timetable", timetable);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DataAccessException(e.getLocalizedMessage());
		} finally {
			HibernateUtil.closeSession();
		}
		return Boolean.TRUE;
	}

	@Override
	public List<Users> getUsersByTrain(String name) throws DataAccessException,
			InvalidInputException {
		List<Users> users = new ArrayList<Users>();
		try {
			HibernateUtil.beginTransaction();
			Trains train = (Trains) HibernateUtil.getSession()
					.createCriteria(Trains.class)
					.add(Restrictions.eq("name", name)).uniqueResult();
			if (train == null) {
				throw new InvalidInputException("Train " + name
						+ " does not exist");
			}
			Timetable timetable = (Timetable) HibernateUtil.getSession()
					.createCriteria(Timetable.class)
					.add(Restrictions.eq("trains", train)).uniqueResult();
			@SuppressWarnings("unchecked")
			List<Tickets> tickets = (List<Tickets>) HibernateUtil.getSession()
					.createCriteria(Tickets.class)
					.add(Restrictions.eq("timetable", timetable)).list();
			Iterator<Tickets> ticketIterator = tickets.iterator();
			while (ticketIterator.hasNext()) {
				Tickets ticket = ticketIterator.next();
				users.add(ticket.getUsers());
			}
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DataAccessException(e.getLocalizedMessage());
		} finally {
			HibernateUtil.closeSession();
		}
		return users;
	}
}