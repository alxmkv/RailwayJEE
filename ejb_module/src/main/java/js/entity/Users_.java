package js.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-03-19T13:20:39.326+0400")
@StaticMetamodel(Users.class)
public class Users_ {
	public static volatile SingularAttribute<Users, Long> id;
	public static volatile SingularAttribute<Users, String> login;
	public static volatile SingularAttribute<Users, String> password;
	public static volatile SingularAttribute<Users, String> email;
	public static volatile SingularAttribute<Users, String> name;
	public static volatile SingularAttribute<Users, String> surname;
	public static volatile SingularAttribute<Users, Date> birthdate;
	public static volatile SingularAttribute<Users, Byte> userType;
	public static volatile SingularAttribute<Users, Byte> status;
	public static volatile SingularAttribute<Users, Date> tsLogin;
	public static volatile SetAttribute<Users, Tickets> tickets;
}
