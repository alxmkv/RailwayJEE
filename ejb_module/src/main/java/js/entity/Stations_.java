package js.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-03-19T13:20:38.967+0400")
@StaticMetamodel(Stations.class)
public class Stations_ {
	public static volatile SingularAttribute<Stations, Long> id;
	public static volatile SingularAttribute<Stations, String> name;
	public static volatile SetAttribute<Stations, Timetable> timetablesForDepartureStationId;
	public static volatile SetAttribute<Stations, Timetable> timetablesForArrivalStationId;
}
