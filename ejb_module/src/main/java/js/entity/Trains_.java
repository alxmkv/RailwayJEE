package js.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-03-23T15:28:00.966+0400")
@StaticMetamodel(Trains.class)
public class Trains_ {
	public static volatile SingularAttribute<Trains, Long> id;
	public static volatile SingularAttribute<Trains, Integer> number;
	public static volatile SingularAttribute<Trains, String> name;
	public static volatile SingularAttribute<Trains, Integer> capacity;
	public static volatile SetAttribute<Trains, Timetable> timetables;
}
