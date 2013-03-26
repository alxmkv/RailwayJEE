package js.dao;

import java.util.Date;

import js.entity.Stations;
import js.entity.Trains;
import js.entity.Users;
import js.exception.DataAccessException;
import js.exception.InvalidInputException;
import js.exception.TicketOrderFailedException;

/**
 * @author Alexander Markov
 */
public interface TicketDAO {
	/**
	 * @param user
	 * @param train
	 * @param departureStation
	 * @param arrivalStation
	 * @param date
	 * @param departureTime
	 * @param arrivalTime
	 * @return <code>true</code> if a ticket is successfully bought
	 * @throws DataAccessException
	 * @throws InvalidInputException
	 * @throws TicketOrderFailedExceptions
	 */
	public Boolean buyTicket(Users user, Trains train,
			Stations departureStation, Stations arrivalStation, Date date,
			Date departureTime, Date arrivalTime) throws DataAccessException,
			TicketOrderFailedException, InvalidInputException;
}