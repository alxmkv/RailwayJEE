package js.web.cdi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import js.dao.HibernateUtil;
import js.dto.TimetableServiceDTO;
import js.exception.DataAccessException;
import js.exception.InvalidInputException;
import js.service.TimetableService;
import js.web.dto.Timetable;
import js.web.dto.TimetableDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Alexander Markov
 */
@RequestScoped
@Named
public class TimetableServiceBean {

	private static final Logger logger = LogManager
			.getLogger(TimetableServiceBean.class);

	@EJB
	private TimetableService timetableService;

	private TimetableDTO timetableDTO = new TimetableDTO();

	private List<Timetable> timetableList = new ArrayList<Timetable>();

	public void getTimetableFromAToBInTimeInterval() {
		HibernateUtil.init();
		// timetableList = new ArrayList<Timetable>();
		try {
			if ("".equals(timetableDTO.getArrivalStation())) {
				// [train number - train name, arrival station, departure time,
				// arrival time, capacity]
				Map<Integer, TimetableServiceDTO> timetables = timetableService
						.getTimetableByStation(timetableDTO
								.getDepartureStation());
				for (Integer trainNumber : timetables.keySet()) {
					SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
					timetableList.add(new Timetable(trainNumber.toString(),
							timetables.get(trainNumber).getTrainName(),
							timetables.get(trainNumber).getArrivalStation(),
							timeFormat.format(timetables.get(trainNumber)
									.getDepartureTime()), timeFormat
									.format(timetables.get(trainNumber)
											.getArrivalTime()), timetables
									.get(trainNumber).getTicketsLeft()
									.toString()));
				}
			} else {
				// [train number - train name, departure time, arrival time,
				// capacity]
				SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
				Map<Integer, TimetableServiceDTO> timetables = timetableService
						.getTimetableFromAToBInTimeInterval(
								timetableDTO.getDepartureStation(),
								timetableDTO.getArrivalStation(),
								timeFormat.parse(timetableDTO.getTimeFrom()),
								timeFormat.parse(timetableDTO.getTimeTo()));
				for (Integer trainNumber : timetables.keySet()) {
					timetableList.add(new Timetable(trainNumber.toString(),
							timetables.get(trainNumber).getTrainName(),
							timeFormat.format(timetables.get(trainNumber)
									.getDepartureTime()), timeFormat
									.format(timetables.get(trainNumber)
											.getArrivalTime()), timetables
									.get(trainNumber).getTicketsLeft()
									.toString()));
				}
			}
		} catch (DataAccessException e) {
			logger.error(e.getLocalizedMessage());
			showMessage("Could not get timetable for "
					+ timetableDTO.getDepartureStation());
		} catch (InvalidInputException e) {
			showMessage(e.getLocalizedMessage());
		} catch (ParseException e) {
			logger.error(e.getLocalizedMessage());
			showMessage("Incorrect time format: hh:mm");
		}
		// return timetableList;
	}

	public List<Timetable> getTimetableList() {
		return timetableList;
	}

	public TimetableDTO getTimetableDTO() {
		return timetableDTO;
	}

	private void showMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(message));
	}
}