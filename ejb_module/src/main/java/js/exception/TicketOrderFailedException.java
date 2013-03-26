package js.exception;

/**
 * @author Alexander Markov
 */
public class TicketOrderFailedException extends Exception {

	private static final long serialVersionUID = 3812769539038457815L;

	/**
	 * @param message
	 */
	public TicketOrderFailedException(String message) {
		super(message);
	}
}