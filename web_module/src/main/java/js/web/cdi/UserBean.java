package js.web.cdi;

import java.io.Serializable;

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
public class UserBean implements Serializable {

	private static final long serialVersionUID = 4973271053978726749L;
	private static final Logger logger = LogManager.getLogger(UserBean.class);

	@EJB
	private UserService userService;

	private User user = new User();

	public String authenticateUser() {
		HibernateUtil.init();
		try {
			int userType = userService.authenticateUser(user.getLogin(),
					user.getPassword());
			if (userType != -1) {
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("login", user.getLogin());
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("type", new Integer(userType));
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
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put(user.getLogin(), new Integer(2));
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
		return "index";
	}

	private void showMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(message));
	}

	public User getUser() {
		return user;
	}
}