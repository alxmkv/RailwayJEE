package js.web.cdi;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import js.dao.HibernateUtil;
import js.dto.TicketDTO;
import js.exception.DataAccessException;
import js.exception.InvalidInputException;
import js.exception.UserRegistrationFailedException;
import js.service.UserService;
import js.web.dto.Ticket;
import js.web.dto.User;
import js.web.dto.UserDTO;

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

	private List<Ticket> ticketList;
	private List<UserDTO> userList;
	private User user = new User();
	private String login;
	private String userType;
	private List<String> userLoginList;

	public List<String> userLoginAutoComplete(String query) {
		if (userLoginList == null || userLoginList.isEmpty()) {
			userLoginList = new ArrayList<String>();
			try {
				Map<String, js.dto.UserDTO> users = userService.getAllUsers();
				for (String login : users.keySet()) {
					userLoginList.add(login);
				}
			} catch (DataAccessException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
		List<String> results = new ArrayList<String>();
		Iterator<String> iter = userLoginList.iterator();
		while (iter.hasNext()) {
			String userLogin = iter.next();
			if (userLogin.toLowerCase().startsWith(query)) {
				results.add(userLogin);
			}
		}
		return results;
	}

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
				showDetailedMessage(FacesMessage.SEVERITY_ERROR,
						"Incorrect login or password", "");
			}
			System.out.println("User type: " + userType);
		} catch (DataAccessException e) {
			showDetailedMessage(FacesMessage.SEVERITY_ERROR,
					"Incorrect login or password", "");
			logger.error(e.getLocalizedMessage());
		}
		return "";
	}

	public String registerUser() {
		HibernateUtil.init();
		try {
			if (userService.registerUser(user.getLogin(), user.getPassword(),
					user.getEmail(), user.getName(), user.getSurname(),
					user.getBirthdate())) {
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("login", user.getLogin());
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("type", new Integer(2));
				return "main";
			} else {
				showDetailedMessage(FacesMessage.SEVERITY_ERROR,
						"Registration failed", "Please, try again");
			}
		} catch (UserRegistrationFailedException e) {
			showDetailedMessage(FacesMessage.SEVERITY_ERROR,
					e.getLocalizedMessage(), "");
			logger.error(e.getLocalizedMessage());
		} catch (DataAccessException e) {
			showDetailedMessage(FacesMessage.SEVERITY_ERROR,
					"Registration failed", "Please, try again");
			logger.error(e.getLocalizedMessage());
		}
		return "";
	}

	public List<Ticket> getTicketList() {
		ticketList = new ArrayList<Ticket>();
		if (!FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().containsKey("login")) {
			return ticketList;
		}
		HibernateUtil.init();
		try {
			Map<Long, TicketDTO> tickets = userService
					.getTicketsByUser((String) FacesContext
							.getCurrentInstance().getExternalContext()
							.getSessionMap().get("login"));
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			for (Long id : tickets.keySet()) {
				TicketDTO ticketDTO = tickets.get(id);
				ticketList.add(new Ticket(
						dateFormat.format(ticketDTO.getDate()), ticketDTO
								.getDepartureStation(), ticketDTO
								.getArrivalStation(), timeFormat
								.format(ticketDTO.getDepartureTime()),
						timeFormat.format(ticketDTO.getArrivalTime()),
						ticketDTO.getTrainNumber().toString(), ticketDTO
								.getTrainName(), Long.toString(id)));
			}
		} catch (DataAccessException e) {
			showDetailedMessage(FacesMessage.SEVERITY_ERROR,
					"Could not get list of tickets", "");
			logger.error(e.getLocalizedMessage());
		}
		Collections.sort(ticketList);
		return ticketList;
	}

	public List<UserDTO> getUserList() {
		userList = new ArrayList<UserDTO>();
		if (!FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().containsKey("login")) {
			return userList;
		}
		HibernateUtil.init();
		try {
			Map<String, js.dto.UserDTO> users = userService.getAllUsers();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			for (String login : users.keySet()) {
				js.dto.UserDTO user = users.get(login);
				String userType = (user.getUserType() == 1) ? "admin"
						: "passenger";
				userList.add(new UserDTO(login, user.getEmail(),
						user.getName(), user.getSurname(), dateFormat
								.format(user.getBirthdate()), userType));
			}
		} catch (DataAccessException e) {
			showDetailedMessage(FacesMessage.SEVERITY_ERROR,
					"Could not get list of users", "");
			logger.error(e.getLocalizedMessage());
		}
		Collections.sort(userList);
		return userList;
	}

	public void changeAccessRights() {
		System.out.println("login = " + login + "; userType = " + userType);
		if (login != null && !"".equalsIgnoreCase(login) && userType != null
				&& !"".equalsIgnoreCase(userType)) {
			System.out.println("changeAccessRights()");
			Byte userTypeByte = "1".equalsIgnoreCase(userType) ? Byte
					.valueOf("1") : Byte.valueOf("2");
			try {
				if (userService.setAccessRights(login, userTypeByte)) {
					showDetailedMessage(FacesMessage.SEVERITY_INFO,
							"User access rights changed successful!", "");
				}
			} catch (DataAccessException e) {
				showDetailedMessage(FacesMessage.SEVERITY_ERROR,
						"Could not update user access rights", "");
				logger.error(e.getLocalizedMessage());
			} catch (InvalidInputException e) {
				showDetailedMessage(FacesMessage.SEVERITY_ERROR,
						e.getLocalizedMessage(), "");
			}
		}
	}

	private void showDetailedMessage(Severity severity, String summary,
			String detail) {
		Iterator<FacesMessage> iter = FacesContext.getCurrentInstance()
				.getMessages();
		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(severity, summary, detail));
	}

	public User getUser() {
		return user;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}