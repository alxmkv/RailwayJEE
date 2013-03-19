package js.exception;

/**
 * @author Alexander Markov
 */
public class UserRegistrationFailedException extends Exception {

	private static final long serialVersionUID = -42314549269643748L;

	/**
	 * @param message
	 */
	public UserRegistrationFailedException(String message) {
		super(message);
	}
}