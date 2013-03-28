package js.web.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Alexander Markov
 */
public class TimetableDTO implements Serializable {

	private static final long serialVersionUID = 7936319431478840593L;
	private String departureStation;
	private String arrivalStation;
	private Date date;
	private Date timeFrom;
	private Date timeTo;

	public String getDepartureStation() {
		return departureStation;
	}

	public void setDepartureStation(String departureStation) {
		this.departureStation = departureStation;
	}

	public String getArrivalStation() {
		return arrivalStation;
	}

	public void setArrivalStation(String arrivalStation) {
		this.arrivalStation = arrivalStation;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(Date departureTime) {
		this.timeFrom = departureTime;
	}

	public Date getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(Date arrivalTime) {
		this.timeTo = arrivalTime;
	}
}