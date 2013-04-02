package js.web.cdi;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import js.dao.HibernateUtil;
import js.dto.TimetableServiceDTO;
import js.exception.DataAccessException;
import js.exception.InvalidInputException;
import js.exception.TicketOrderFailedException;
import js.service.StationService;
import js.service.TicketService;
import js.service.TimetableService;
import js.web.dto.Timetable;
import js.web.dto.TimetableDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Alexander Markov
 */
// @javax.faces.bean.ManagedBean
// @javax.faces.bean.RequestScoped
@SessionScoped
@Named
public class TimetableServiceBean implements Serializable {

	private static final long serialVersionUID = -5713965920223977447L;
	private static final Logger logger = LogManager
			.getLogger(TimetableServiceBean.class);

	@EJB
	private TimetableService timetableService;
	@EJB
	private TicketService ticketService;
	@EJB
	private StationService stationService;

	private TimetableDTO timetableDTO = new TimetableDTO();
	private Timetable selectedTimetableRow;

	private List<Timetable> timetableList;
	private List<String> stationList;

	public List<String> stationAutoComplete(String query) {
		if (stationList == null || stationList.isEmpty()) {
			stationList = new ArrayList<String>();
			try {
				Set<String> stations = stationService.getAllStations();
				Iterator<String> iter = stations.iterator();
				while (iter.hasNext()) {
					stationList.add(iter.next());
				}
			} catch (DataAccessException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
		List<String> results = new ArrayList<String>();
		Iterator<String> iter = stationList.iterator();
		while (iter.hasNext()) {
			String station = iter.next();
			if (station.toLowerCase().startsWith(query)) {
				results.add(station);
			}
		}
		return results;
	}

	public List<Timetable> getTimetableList() {
		timetableList = new ArrayList<Timetable>();
		if (!FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().containsKey("login")) {
			return timetableList;
		}
		if (timetableDTO.getDepartureStation() != null
				&& timetableDTO.getArrivalStation() != null
				&& timetableDTO.getDate() != null
				&& timetableDTO.getTimeFrom() != null
				&& timetableDTO.getTimeTo() != null) {
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
						clearMessages();
					}
				} else {
					// [train number - train name, departure time, arrival
					// time,
					// capacity]
					Map<Integer, TimetableServiceDTO> timetables = timetableService
							.getTimetableFromAToBInTimeInterval(
									timetableDTO.getDepartureStation(),
									timetableDTO.getArrivalStation(),
									timetableDTO.getDate(),
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
						clearMessages();
					}
				}
			} catch (DataAccessException e) {
				showDetailedMessage(
						FacesMessage.SEVERITY_ERROR,
						"Could not get timetable for "
								+ timetableDTO.getDepartureStation(), "");
				logger.error(e.getLocalizedMessage());
			} catch (InvalidInputException e) {
				showDetailedMessage(FacesMessage.SEVERITY_ERROR,
						e.getLocalizedMessage(), "");
			}
			Collections.sort(timetableList);
			return timetableList;
		}
		return timetableList;
	}

	public void buyTicket() {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		if (timetableDTO.getDate() == null) {
			showDetailedMessage(FacesMessage.SEVERITY_ERROR,
					"Please enter date", "");
			return;
		}
		try {
			if (!ticketService.buyTicket(""
					+ FacesContext.getCurrentInstance().getExternalContext()
							.getSessionMap().get("login"),
					Integer.parseInt(selectedTimetableRow.getTrainNumber()),
					timetableDTO.getDepartureStation(),
					selectedTimetableRow.getArrivalStation(),
					timetableDTO.getDate(),
					timeFormat.parse(selectedTimetableRow.getDepartureTime()),
					timeFormat.parse(selectedTimetableRow.getArrivalTime()))) {
				showDetailedMessage(FacesMessage.SEVERITY_ERROR,
						"Could not buy ticket", "");
			} else {
				showDetailedMessage(
						FacesMessage.SEVERITY_INFO,
						"Ticket order successful!",
						"From: " + timetableDTO.getDepartureStation()
								+ "\nTo: "
								+ selectedTimetableRow.getArrivalStation()
								+ "\nDate: " + timetableDTO.getDate()
								+ "\nDeparture time: "
								+ selectedTimetableRow.getDepartureTime()
								+ "\nArrival time: "
								+ selectedTimetableRow.getArrivalTime());
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

	private void clearMessages() {
		Iterator<FacesMessage> iter = FacesContext.getCurrentInstance()
				.getMessages();
		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}
	}

	private void showDetailedMessage(Severity severity, String summary,
			String detail) {
		clearMessages();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(severity, summary, detail));
	}

	public void setTimetableList(List<Timetable> timetableList) {
		this.timetableList = timetableList;
	}

	public TimetableDTO getTimetableDTO() {
		return timetableDTO;
	}

	public Timetable getSelectedTimetableRow() {
		return selectedTimetableRow;
	}

	public void setSelectedTimetableRow(Timetable selectedTimetableRow) {
		this.selectedTimetableRow = selectedTimetableRow;
	}
	// @PostConstruct
	// public void init() {
	// }
}