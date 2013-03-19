/**
 * 
 */
package js.exception;

/**
 * @author Alexander Markov
 */
public class UserAuthenticationFailedException extends Exception {

	private static final long serialVersionUID = -6093827940979982011L;

	public UserAuthenticationFailedException(String message) {
		super(message);
	}
}
