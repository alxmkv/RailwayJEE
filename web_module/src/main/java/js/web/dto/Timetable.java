package js.web.dto;

import java.io.Serializable;

/**
 * @author Alexander Markov
 */
public class Timetable implements Serializable, Comparable<Timetable> {
	private static final long serialVersionUID = 7335204061452992531L;
	private String trainNumber;
	private String trainName;
	private String arrivalStation;
	private String departureTime;
	private String arrivalTime;
	private String ticketsLeft;

	@Override
	public int compareTo(Timetable timetable) {
		return departureTime.compareTo(timetable.getDepartureTime());
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

	public Timetable() {
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