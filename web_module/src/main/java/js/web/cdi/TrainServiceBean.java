package js.web.cdi;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import js.dao.HibernateUtil;
import js.dto.TrainServiceDTO;
import js.exception.DataAccessException;
import js.service.TrainService;
import js.web.dto.Train;

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

	private List<Train> trainList;

	public List<Train> getTrainList() {
		trainList = new ArrayList<Train>();
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
}