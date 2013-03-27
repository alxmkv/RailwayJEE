package js.web.dto;

import java.util.Date;

/**
 * @author Alexander Markov
 */
public class TimetableDTO {
	private String departureStation;
	private String arrivalStation;
	private Date date;
	private Date timeFrom;
	private Date timeTo;
	private int counter = 0;

	public String getDepartureStation() {
		if (departureStation != null) {
			counter++;
		}
		return departureStation;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
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