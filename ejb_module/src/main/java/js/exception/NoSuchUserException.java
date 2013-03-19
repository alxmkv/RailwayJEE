package js.exception;

/**
 * @author Alexander Markov
 */
public class NoSuchUserException extends Exception {

	private static final long serialVersionUID = -8241917485181048402L;

	public NoSuchUserException(String message) {
		super(message);
	}
}