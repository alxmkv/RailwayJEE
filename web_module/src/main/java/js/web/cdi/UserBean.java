package js.web.cdi;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import js.dao.HibernateUtil;
import js.exception.DataAccessException;
import js.exception.UserRegistrationFailedException;
import js.service.UserService;
import js.web.dto.User;

/**
 * http://localhost:8080/web_module/index.xhtml User bean is available for one
 * user across multiple pages. Different users who use the web application are
 * given different instances of the bean object
 * 
 * @author Alexander Markov
 */
@RequestScoped
@Named
// @SessionScoped
// @ViewScoped
public class UserBean {

	private static final Logger logger = LogManager.getLogger(UserBean.class);

	@EJB
	private UserService userService;

	private User user = new User();

	public String authenticateUser() {
		HibernateUtil.init();
		try {
			if (userService.authenticateUser(user.getLogin(),
					user.getPassword())) {
				// TODO: send to main page
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put(user.getLogin(), "");
				return "main";
			} else {
				showMessage("Incorrect login or password");
			}
		} catch (DataAccessException e) {
			logger.error(e.getLocalizedMessage());
			showMessage("Incorrect login or password");
		}
		return "index";
	}

	// @Test
	// public void authenticateUserTest() throws DataAccessException {
	// HibernateUtil.init();
	// Assert.assertEquals(true,
	// new UserService().authenticateUser("test", "test"));
	// }

	public String registerUser() {
		HibernateUtil.init();
		try {
			if (userService.registerUser(user.getLogin(), user.getPassword(),
					user.getEmail(), user.getName(), user.getSurname(),
					user.getBirthdate())) {
				return "main";
			} else {
				showMessage("Registration failed. Please, try again.");
			}
		} catch (UserRegistrationFailedException e) {
			logger.error(e.getLocalizedMessage());
			showMessage(e.getLocalizedMessage());
		} catch (DataAccessException e) {
			logger.error(e.getLocalizedMessage());
			showMessage("Registration failed. Please, try again.");
		}
		return "";
	}

	private void showMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(message));
	}

	public User getUser() {
		return user;
	}

	// public UserBean() {
	// user = new User();
	// }
}