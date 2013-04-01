package js.dto;

import java.util.Date;

/**
 * @author Alexander Markov
 */
public class TicketDTO {

	private Date date;
	private String departureStation;
	private String arrivalStation;
	private Date departureTime;
	private Date arrivalTime;
	private Integer trainNumber;
	private String trainName;

	public TicketDTO(Date date, String departureStation, String arrivalStation,
			Date departureTime, Date arrivalTime, Integer trainNumber,
			String trainName) {
		this.date = date;
		this.departureStation = departureStation;
		this.arrivalStation = arrivalStation;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.trainNumber = trainNumber;
		this.trainName = trainName;
	}

	public Date getDate() {
		return date;
	}

	public String getDepartureStation() {
		return departureStation;
	}

	public String getArrivalStation() {
		return arrivalStation;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public Integer getTrainNumber() {
		return trainNumber;
	}

	public String getTrainName() {
		return trainName;
	}
}