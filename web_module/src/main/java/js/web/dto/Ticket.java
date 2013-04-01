package js.web.dto;

import java.io.Serializable;

/**
 * @author Alexander Markov
 */
public class Ticket implements Serializable, Comparable<Ticket> {

	private static final long serialVersionUID = -6480020606977616165L;
	private String date;
	private String departureStation;
	private String arrivalStation;
	private String departureTime;
	private String arrivalTime;
	private String trainNumber;
	private String trainName;
	private String id;

	@Override
	public int compareTo(Ticket ticket) {
		return date.compareTo(ticket.getDate());
	}

	public Ticket() {
	}

	public Ticket(String date, String departureStation, String arrivalStation,
			String departureTime, String arrivalTime, String trainNumber,
			String trainName, String id) {
		this.date = date;
		this.departureStation = departureStation;
		this.arrivalStation = arrivalStation;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.trainNumber = trainNumber;
		this.trainName = trainName;
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}