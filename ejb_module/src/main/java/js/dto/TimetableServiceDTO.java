package js.dto;

import java.util.Date;

/**
 * @author Alexander Markov
 */
public class TimetableServiceDTO {
	private String trainName;
	private String arrivalStation;
	private Date departureTime;
	private Date arrivalTime;
	private Integer ticketsLeft;

	public TimetableServiceDTO(String trainName, String arrivalStation,
			Date departureTime, Date arrivalTime, Integer ticketsLeft) {
		this.trainName = trainName;
		this.arrivalStation = arrivalStation;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.ticketsLeft = ticketsLeft;
	}

	public String getTrainName() {
		return trainName;
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

	public Integer getTicketsLeft() {
		return ticketsLeft;
	}
}