package js.exception;

/**
 * @author Alexander Markov
 */
public class InvalidInputException extends Exception {

	private static final long serialVersionUID = 6370381735282845601L;

	/**
	 * @param message
	 */
	public InvalidInputException(String message) {
		super(message);
	}
}