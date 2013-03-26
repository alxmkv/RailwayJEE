package js.web.dto;

/**
 * @author Alexander Markov
 */
public class TimetableDTO {
	private String departureStation;
	private String arrivalStation;
	private String timeFrom;
	private String timeTo;

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

	public String getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(String departureTime) {
		this.timeFrom = departureTime;
	}

	public String getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(String arrivalTime) {
		this.timeTo = arrivalTime;
	}
}