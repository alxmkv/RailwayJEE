package js.exception;

/**
 * @author Alexander Markov
 */
public class UnauthorizedAccessException extends Exception {

	private static final long serialVersionUID = -4493782324902035353L;

	public UnauthorizedAccessException(String message) {
		super(message);
	}
}