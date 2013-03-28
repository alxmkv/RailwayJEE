package js.dto;

import java.util.Date;

/**
 * @author Alexander Markov
 */
public class TrainServiceDTO {
	private String trainName;
	private String departureStation;
	private String arrivalStation;
	private Date departureTime;
	private Date arrivalTime;
	private Integer trainCapacity;

	public TrainServiceDTO(String trainName, String departureStation,
			String arrivalStation, Date departureTime, Date arrivalTime,
			Integer trainCapacity) {
		this.trainName = trainName;
		this.departureStation = departureStation;
		this.arrivalStation = arrivalStation;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.trainCapacity = trainCapacity;
	}

	public String getTrainName() {
		return trainName;
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

	public Integer getTrainCapacity() {
		return trainCapacity;
	}
}