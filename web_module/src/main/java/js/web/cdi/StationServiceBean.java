package js.web.cdi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import js.dao.HibernateUtil;
import js.exception.DataAccessException;
import js.service.StationService;
import js.web.dto.Station;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Alexander Markov
 */
@RequestScoped
@Named
public class StationServiceBean implements Serializable {

	private static final long serialVersionUID = -167036411207543992L;
	private static final Logger logger = LogManager
			.getLogger(StationServiceBean.class);

	@EJB
	private StationService stationService;

	private List<Station> stationList;
	private String station;

	public List<Station> getStationList() {
		stationList = new ArrayList<Station>();
		if (!FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().containsKey("login")) {
			return stationList;
		}
		HibernateUtil.init();
		try {
			Set<String> stations = stationService.getAllStations();
			Iterator<String> iter = stations.iterator();
			while (iter.hasNext()) {
				stationList.add(new Station(iter.next()));
			}
		} catch (DataAccessException e) {
			showDetailedMessage(FacesMessage.SEVERITY_ERROR,
					"Could not get list of stations", "");
			logger.error(e.getLocalizedMessage());
		}
		Collections.sort(stationList);
		return stationList;
	}

	public void addStation() {
		try {
			if (stationService.addStation(station)) {
				showDetailedMessage(FacesMessage.SEVERITY_INFO,
						"Station creation successful!", "");
			}
		} catch (DataAccessException e) {
			showDetailedMessage(FacesMessage.SEVERITY_ERROR,
					"This station already exists", "");
			logger.error(e.getLocalizedMessage());
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

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}
}