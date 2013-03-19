package js.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-03-19T13:20:39.123+0400")
@StaticMetamodel(Timetable.class)
public class Timetable_ {
	public static volatile SingularAttribute<Timetable, Long> id;
	public static volatile SingularAttribute<Timetable, Stations> stationsByArrivalStationId;
	public static volatile SingularAttribute<Timetable, Stations> stationsByDepartureStationId;
	public static volatile SingularAttribute<Timetable, Trains> trains;
	public static volatile SingularAttribute<Timetable, Date> departureTime;
	public static volatile SingularAttribute<Timetable, Date> arrivalTime;
	public static volatile SetAttribute<Timetable, Tickets> tickets;
}
