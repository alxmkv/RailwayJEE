package js.service;

import java.util.Date;

import javax.ejb.Stateless;

import js.dao.impl.TicketDAOImpl;
import js.entity.Stations;
import js.entity.Trains;
import js.entity.Users;
import js.exception.DataAccessException;
import js.exception.InvalidInputException;
import js.exception.TicketOrderFailedException;

/**
 * @author Alexander Markov
 */
@Stateless
// @LocalBean
public class TicketService {

	private TicketDAOImpl ticketDAOImpl = new TicketDAOImpl();

	/**
	 * @param userName
	 * @param trainNumber
	 * @param departureStationName
	 * @param arrivalStationName
	 * @param date
	 * @param departureTime
	 * @param arrivalTime
	 * @return <code>true</code> if a ticket is successfully bought
	 * @throws DataAccessException
	 * @throws TicketOrderFailedException
	 * @throws InvalidInputException
	 */
	public Boolean buyTicket(String userName, int trainNumber,
			String departureStationName, String arrivalStationName, Date date,
			Date departureTime, Date arrivalTime) throws DataAccessException,
			TicketOrderFailedException, InvalidInputException {
		Users user = new Users();
		user.setLogin(userName);
		Trains train = new Trains();
		train.setNumber(trainNumber);
		Stations departureStation = new Stations(departureStationName);
		Stations arrivalStation = new Stations(arrivalStationName);
		return ticketDAOImpl.buyTicket(user, train, departureStation,
				arrivalStation, date, departureTime, arrivalTime);
	}
}