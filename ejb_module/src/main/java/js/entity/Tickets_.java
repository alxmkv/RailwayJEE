package js.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-03-19T13:20:39.077+0400")
@StaticMetamodel(Tickets.class)
public class Tickets_ {
	public static volatile SingularAttribute<Tickets, Long> id;
	public static volatile SingularAttribute<Tickets, Users> users;
	public static volatile SingularAttribute<Tickets, Timetable> timetable;
	public static volatile SingularAttribute<Tickets, Date> date;
}
