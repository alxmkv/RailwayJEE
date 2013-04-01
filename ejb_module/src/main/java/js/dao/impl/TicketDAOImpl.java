package js.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import javax.ejb.Stateless;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import js.dao.HibernateUtil;
import js.dao.TicketDAO;
import js.entity.Stations;
import js.entity.Tickets;
import js.entity.Timetable;
import js.entity.Trains;
import js.entity.Users;
import js.exception.DataAccessException;
import js.exception.InvalidInputException;
import js.exception.TicketOrderFailedException;

/**
 * @author Alexander Markov
 */
@Stateless
// @Local(TicketDAO.class)
// @LocalBean
public class TicketDAOImpl implements TicketDAO {
	@Override
	public Boolean buyTicket(Users user, Trains train,
			Stations departureStation, Stations arrivalStation, Date date,
			Date departureTime, Date arrivalTime) throws DataAccessException,
			TicketOrderFailedException, InvalidInputException {
		try {
			HibernateUtil.beginTransaction();
			Users userT = (Users) HibernateUtil.getSession()
					.createCriteria(Users.class)
					.add(Restrictions.eq("login", user.getLogin()))
					.uniqueResult();
			if (userT == null) {
				throw new InvalidInputException("User " + user.getLogin()
						+ " does not exist");
			}
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
			Trains trainT = (Trains) HibernateUtil.getSession()
					.createCriteria(Trains.class)
					.add(Restrictions.eq("number", train.getNumber()))
					.uniqueResult();
			if (trainT == null) {
				throw new InvalidInputException("Train " + train.getName()
						+ " does not exist");
			}
			Timetable timetable = (Timetable) HibernateUtil
					.getSession()
					.createCriteria(Timetable.class)
					.add(Restrictions.eq("stationsByDepartureStationId",
							stationA))
					.add(Restrictions
							.eq("stationsByArrivalStationId", stationB))
					.add(Restrictions.eq("trains", trainT))
					.add(Restrictions.eq("departureTime", departureTime))
					.add(Restrictions.eq("arrivalTime", arrivalTime))
					.uniqueResult();
			if (timetable == null) {
				throw new InvalidInputException("Incorrect departure ("
						+ departureTime + ") or arrival time (" + arrivalTime
						+ ")");
			}
			// 1. Check if there are any available tickets for this train and
			// date
			Iterator<Tickets> ticketIter = timetable.getTickets().iterator();
			int ticketCounter = 0;
			while (ticketIter.hasNext()) {
				if (ticketIter.next().getDate().equals(date)) {
					ticketCounter++;
				}
			}
			if (ticketCounter >= trainT.getCapacity()) {
				throw new TicketOrderFailedException(
						"No tickets available for " + trainT.getName()
								+ " train for " + date);
			}
			// 2. Check whether this user has already bought a ticket for this
			// train and date
			String login = userT.getLogin();
			Iterator<Tickets> iter = timetable.getTickets().iterator();
			while (iter.hasNext()) {
				Tickets t = iter.next();
				if (login.equals(t.getUsers().getLogin())
						&& date.equals(t.getDate())) {
					throw new TicketOrderFailedException(
							"You have already bought a ticket for "
									+ trainT.getName() + " train for " + date);
				}
			}
			// 3. Check if (train departure time - current time) > 10 minutes
			// for this train and date
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
			Date currentDateTime = new Date();
			try {
				currentDateTime = dateTimeFormat.parse(dateTimeFormat
						.format(currentDateTime));
				Date ticketDateTime = dateTimeFormat.parse(dateFormat
						.format(date) + " " + timeFormat.format(departureTime));
				if (((ticketDateTime.getTime() - currentDateTime.getTime()) / (1000 * 60)) <= 10) {
					throw new TicketOrderFailedException(
							"No tickets available for " + trainT.getName()
									+ " train for " + date);
				}
			} catch (ParseException e) {
				throw new DataAccessException(e.getLocalizedMessage());
			}
			Tickets ticket = new Tickets(userT, timetable, date);
			HibernateUtil.getSession().saveOrUpdate("Tickets", ticket);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DataAccessException(e.getLocalizedMessage());
		} finally {
			HibernateUtil.closeSession();
		}
		return Boolean.TRUE;
	}
}