package js.web.cdi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import js.dao.HibernateUtil;
import js.dto.TimetableServiceDTO;
import js.exception.DataAccessException;
import js.exception.InvalidInputException;
import js.exception.TicketOrderFailedException;
import js.service.TicketService;
import js.service.TimetableService;
import js.web.dto.Timetable;
import js.web.dto.TimetableDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Alexander Markov
 */
@javax.enterprise.context.RequestScoped
// @javax.faces.bean.ManagedBean
// @javax.faces.bean.RequestScoped
@Named
public class TimetableServiceBean {

	private static final Logger logger = LogManager
			.getLogger(TimetableServiceBean.class);

	@EJB
	private TimetableService timetableService;
	@EJB
	private TicketService ticketService;

	private TimetableDTO timetableDTO = new TimetableDTO();

	private List<Timetable> timetableList;
	private Timetable selectedTimetableRow;

	public void buyTicket() {
		System.out.println("buyTicket()");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		try {
			System.out.println(FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().keySet().iterator()
					.next());
			if (!ticketService.buyTicket(FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().keySet().iterator()
					.next(),
					Integer.parseInt(selectedTimetableRow.getTrainNumber()),
					timetableDTO.getDepartureStation(),
					selectedTimetableRow.getArrivalStation(),
					timetableDTO.getDate(),
					timeFormat.parse(selectedTimetableRow.getDepartureTime()),
					timeFormat.parse(selectedTimetableRow.getArrivalTime()))) {
				showDetailedMessage(FacesMessage.SEVERITY_ERROR,
						"Could not buy ticket", "");
			} else {
				showDetailedMessage(FacesMessage.SEVERITY_INFO,
						"Ticket for order successful!", " - "
								+ selectedTimetableRow.getArrivalStation()
								+ " on " + timetableDTO.getDate() + " "
								+ selectedTimetableRow.getDepartureTime());
			}
		} catch (NumberFormatException e) {
			showDetailedMessage(FacesMessage.SEVERITY_ERROR,
					"Could not buy ticket", "");
			logger.error(e.getLocalizedMessage());
		} catch (DataAccessException e) {
			showDetailedMessage(FacesMessage.SEVERITY_ERROR,
					"Could not buy ticket. Please, try again", "");
			logger.error(e.getLocalizedMessage());
		} catch (TicketOrderFailedException e) {
			showDetailedMessage(FacesMessage.SEVERITY_ERROR,
					e.getLocalizedMessage(), "");
			logger.error(e.getLocalizedMessage());
		} catch (InvalidInputException e) {
			showDetailedMessage(FacesMessage.SEVERITY_ERROR,
					e.getLocalizedMessage(), "");
			logger.error(e.getLocalizedMessage());
		} catch (ParseException e) {
			showDetailedMessage(FacesMessage.SEVERITY_ERROR,
					"Incorrect date or time", "");
			logger.error(e.getLocalizedMessage());
		}
	}

	public List<Timetable> getTimetableList() {
		if (timetableDTO.getDepartureStation() != null
				&& timetableDTO.getCounter() == 1) {
			timetableList = new ArrayList<Timetable>();
			HibernateUtil.init();
			try {
				SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
				if ("".equals(timetableDTO.getArrivalStation())
						|| timetableDTO.getArrivalStation() == null) {
					// [train number - train name, arrival station,
					// departure time,
					// arrival time, capacity]
					Map<String, TimetableServiceDTO> timetables = timetableService
							.getTimetableByStation(timetableDTO
									.getDepartureStation());
					for (String trainNumberString : timetables.keySet()) {
						timetableList.add(new Timetable(trainNumberString,
								timetables.get(trainNumberString)
										.getTrainName(), timetables.get(
										trainNumberString).getArrivalStation(),
								timeFormat.format(timetables.get(
										trainNumberString).getDepartureTime()),
								timeFormat.format(timetables.get(
										trainNumberString).getArrivalTime()),
								timetables.get(trainNumberString)
										.getTicketsLeft().toString()));
					}
				} else {
					// [train number - train name, departure time, arrival
					// time,
					// capacity]
					Map<Integer, TimetableServiceDTO> timetables = timetableService
							.getTimetableFromAToBInTimeInterval(
									timetableDTO.getDepartureStation(),
									timetableDTO.getArrivalStation(),
									timetableDTO.getTimeFrom(),
									timetableDTO.getTimeTo());
					for (Integer trainNumber : timetables.keySet()) {
						timetableList
								.add(new Timetable(trainNumber.toString(),
										timetables.get(trainNumber)
												.getTrainName(), timetables
												.get(trainNumber)
												.getArrivalStation(),
										timeFormat
												.format(timetables.get(
														trainNumber)
														.getDepartureTime()),
										timeFormat.format(timetables.get(
												trainNumber).getArrivalTime()),
										timetables.get(trainNumber)
												.getTicketsLeft().toString()));
					}
				}
			} catch (DataAccessException e) {
				showMessage("Could not get timetable for "
						+ timetableDTO.getDepartureStation());
				logger.error(e.getLocalizedMessage());
			} catch (InvalidInputException e) {
				showMessage(e.getLocalizedMessage());
			}
			Collections.sort(timetableList);
			return timetableList;
		}
		return timetableList;
	}

	public void setTimetableList(List<Timetable> timetableList) {
		this.timetableList = timetableList;
	}

	public TimetableDTO getTimetableDTO() {
		return timetableDTO;
	}

	private void showMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(message));
	}

	private void showDetailedMessage(Severity severity, String summary,
			String detail) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(severity, summary, detail));
	}

	public Timetable getSelectedTimetableRow() {
		System.out.println("getSelectedTimetableRow");
		return selectedTimetableRow;
	}

	public void setSelectedTimetableRow(Timetable selectedTimetableRow) {
		System.out.println("setSelectedTimetableRow");
		this.selectedTimetableRow = selectedTimetableRow;
	}
	// @PostConstruct
	// public void init() {
	// if (timetableList == null) {
	// timetableList = new ArrayList<Timetable>();
	// HibernateUtil.init();
	// try {
	// // [train number - train name, arrival station,
	// // departure time,
	// // arrival time, capacity]
	// Map<String, TimetableServiceDTO> timetables = timetableService
	// .getTimetableByStation("Saint-Petersburg");
	// for (String trainNumberString : timetables.keySet()) {
	// SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
	// timetableList.add(new Timetable(trainNumberString,
	// timetables.get(trainNumberString).getTrainName(),
	// timetables.get(trainNumberString)
	// .getArrivalStation(), timeFormat
	// .format(timetables.get(trainNumberString)
	// .getDepartureTime()), timeFormat
	// .format(timetables.get(trainNumberString)
	// .getArrivalTime()), timetables
	// .get(trainNumberString).getTicketsLeft()
	// .toString()));
	// System.out
	// .println(timetableList.get(0).getArrivalStation());
	// }
	// } catch (DataAccessException e) {
	// logger.error(e.getLocalizedMessage());
	// showMessage("Could not get timetable for "
	// + timetableDTO.getDepartureStation());
	// } catch (InvalidInputException e) {
	// showMessage(e.getLocalizedMessage());
	// }
	// }
	// }
	// @PostConstruct
	// public void init() {
	// }
	// Set<String> users = FacesContext.getCurrentInstance()
	// .getExternalContext().getSessionMap().keySet();
	// String currentLogin = "";
	// for (String login : users) {
	// currentLogin = login;
	// System.out.println("login = " + currentLogin);
	// FacesContext.getCurrentInstance().getExternalContext()
	// .getSessionMap().put(login, timetableList);
	// }
	// return (List<Timetable>) FacesContext.getCurrentInstance()
	// .getExternalContext().getSessionMap().get(currentLogin);
}