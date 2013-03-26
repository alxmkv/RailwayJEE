package js.web.dto;

/**
 * @author Alexander Markov
 */
public class Timetable {
	private String trainNumber;
	private String trainName;
	private String arrivalStation;
	private String departureTime;
	private String arrivalTime;
	private String ticketsLeft;

	public Timetable(String trainNumber, String trainName,
			String departureTime, String arrivalTime, String ticketsLeft) {
		this.trainNumber = trainNumber;
		this.trainName = trainName;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.ticketsLeft = ticketsLeft;
	}

	public Timetable(String trainNumber, String trainName,
			String arrivalStation, String departureTime, String arrivalTime,
			String ticketsLeft) {
		this.trainNumber = trainNumber;
		this.trainName = trainName;
		this.arrivalStation = arrivalStation;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.ticketsLeft = ticketsLeft;
	}

	public String getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getArrivalStation() {
		return arrivalStation;
	}

	public void setArrivalStation(String arrivalStation) {
		this.arrivalStation = arrivalStation;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getTicketsLeft() {
		return ticketsLeft;
	}

	public void setTicketsLeft(String ticketsLeft) {
		this.ticketsLeft = ticketsLeft;
	}
}
