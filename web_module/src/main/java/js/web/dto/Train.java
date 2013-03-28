package js.web.dto;

import java.io.Serializable;

/**
 * @author Alexander Markov
 */
public class Train implements Serializable, Comparable<Train> {
	private static final long serialVersionUID = 7335204061452992531L;
	private String trainNumber;
	private String trainName;
	private String departureStation;
	private String arrivalStation;
	private String departureTime;
	private String arrivalTime;
	private String capacity;

	@Override
	public int compareTo(Train train) {
		return new Integer(trainNumber).compareTo(new Integer(train
				.getTrainNumber()));
	}

	public Train(String trainNumber, String trainName, String departureStation,
			String arrivalStation, String departureTime, String arrivalTime,
			String ticketsLeft) {
		this.trainNumber = trainNumber;
		this.trainName = trainName;
		this.departureStation = departureStation;
		this.arrivalStation = arrivalStation;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.capacity = ticketsLeft;
	}

	public Train() {
	}

	public String getDepartureStation() {
		return departureStation;
	}

	public void setDepartureStation(String departureStation) {
		this.departureStation = departureStation;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
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
}