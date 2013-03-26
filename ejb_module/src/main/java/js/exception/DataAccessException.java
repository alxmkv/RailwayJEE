package js.exception;

/**
 * @author Alexander Markov
 */
public class DataAccessException extends Exception {

	private static final long serialVersionUID = -8241917485181048402L;

	/**
	 * Construct a new database access/programme error exception
	 * 
	 * @param message
	 */
	public DataAccessException(String message) {
		super(message);
	}
}