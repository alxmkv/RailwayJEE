package js.web.cdi;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import js.dto.TrainServiceDTO;
import js.exception.DataAccessException;
import js.exception.InvalidInputException;
import js.service.StationService;
import js.service.TrainService;
import js.web.dto.Train;
import js.web.dto.TrainDTO;
import js.web.dto.UserDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Alexander Markov
 */
@SessionScoped
@Named
public class TrainServiceBean implements Serializable {

	private static final long serialVersionUID = -5713965920223977447L;
	private static final Logger logger = LogManager
			.getLogger(TrainServiceBean.class);

	@EJB
	private TrainService trainService;
	@EJB
	private StationService stationService;

	private TrainDTO train = new TrainDTO();

	private List<Train> trainList;
	private List<UserDTO> passengerList;
	private String trainNumber;
	private Date date;
	private List<String> stationList;

	public List<String> trainNumberAutoComplete(String query) {
		List<String> results = new ArrayList<String>();
		Iterator<Train> iter = trainList.iterator();
		while (iter.hasNext()) {
			Train train = iter.next();
			String trainNumber = train.getTrainNumber();
			if (trainNumber.startsWith(query)) {
				results.add(trainNumber);
			}
		}
		return results;
	}

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

	public List<Train> getTrainList() {
		trainList = new ArrayList<Train>();
		if (!FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().containsKey("login")) {
			return trainList;
		}
		HibernateUtil.init();
		try {
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
			// [train number - train name, arrival station,
			// departure time,
			// arrival time, capacity]
			Map<Integer, TrainServiceDTO> trains = trainService.getAllTrains();
			for (Integer trainNumber : trains.keySet()) {
				TrainServiceDTO trainDTO = trains.get(trainNumber);
				Train train = null;
				if (trainDTO.getDepartureTime() == null
						|| trainDTO.getArrivalTime() == null) {
					train = new Train(trainNumber.toString(),
							trainDTO.getTrainName(),
							trainDTO.getDepartureStation(),
							trainDTO.getArrivalStation(), "", "", trainDTO
									.getTrainCapacity().toString());
				} else {
					train = new Train(trainNumber.toString(),
							trainDTO.getTrainName(),
							trainDTO.getDepartureStation(),
							trainDTO.getArrivalStation(),
							timeFormat.format(trainDTO.getDepartureTime()),
							timeFormat.format(trainDTO.getArrivalTime()),
							trainDTO.getTrainCapacity().toString());
				}
				trainList.add(train);
			}
		} catch (DataAccessException e) {
			showDetailedMessage(FacesMessage.SEVERITY_ERROR,
					"Could not get list of trains", "");
			logger.error(e.getLocalizedMessage());
		}
		Collections.sort(trainList);
		return trainList;
	}

	public void addTrain() {
		try {
			if (trainService.addTrain(Integer.parseInt(train.getTrainNumber()),
					train.getTrainName(),
					Integer.parseInt(train.getCapacity()),
					train.getDepartureStation(), train.getArrivalStation(),
					train.getDepartureTime(), train.getArrivalTime())) {
				showDetailedMessage(FacesMessage.SEVERITY_INFO,
						"Train creation successful!", "");
			}
		} catch (NumberFormatException e) {
			showDetailedMessage(FacesMessage.SEVERITY_ERROR,
					"Incorrect train number or capacity", "");
		} catch (DataAccessException e) {
			showDetailedMessage(FacesMessage.SEVERITY_ERROR,
					"This train already exists", "");
			logger.error(e.getLocalizedMessage());
		} catch (InvalidInputException e) {
			showDetailedMessage(FacesMessage.SEVERITY_ERROR,
					e.getLocalizedMessage(), "");
		}
	}

	public List<UserDTO> getPassengerList() {
		passengerList = new ArrayList<UserDTO>();
		if (!FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().containsKey("login")) {
			return passengerList;
		}
		if (!"".equalsIgnoreCase(trainNumber) && date != null) {
			HibernateUtil.init();
			try {
				Map<String, js.dto.UserDTO> passengers = trainService
						.getUsersByTrain(Integer.parseInt(trainNumber), date);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				for (String login : passengers.keySet()) {
					js.dto.UserDTO user = passengers.get(login);
					String userType = (user.getUserType() == 1) ? "admin"
							: "passenger";
					passengerList.add(new UserDTO(login, user.getEmail(), user
							.getName(), user.getSurname(), dateFormat
							.format(user.getBirthdate()), userType));
				}
			} catch (NumberFormatException e) {
				showDetailedMessage(FacesMessage.SEVERITY_ERROR,
						"Incorrect train number", "");
			} catch (DataAccessException e) {
				showDetailedMessage(
						FacesMessage.SEVERITY_ERROR,
						"Could not get list of passengers for train #"
								+ train.getTrainNumber(), "");
				logger.error(e.getLocalizedMessage());
			} catch (InvalidInputException e) {
				showDetailedMessage(FacesMessage.SEVERITY_ERROR,
						e.getLocalizedMessage(), "");
			}
			Collections.sort(passengerList);
			return passengerList;
		}
		return passengerList;
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

	public TrainDTO getTrain() {
		return train;
	}

	public void setTrain(TrainDTO train) {
		this.train = train;
	}

	public String getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}