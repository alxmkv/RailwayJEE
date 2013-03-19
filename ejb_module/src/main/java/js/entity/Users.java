package js.entity;

// Generated 17.03.2013 12:45:30 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Users generated by hbm2java
 */
@Entity
@Table(name = "users", uniqueConstraints = {
		@UniqueConstraint(columnNames = "login"),
		@UniqueConstraint(columnNames = "password"),
		@UniqueConstraint(columnNames = "email") })
public class Users implements java.io.Serializable {

	private static final long serialVersionUID = 6170283239615115639L;

	private Long id;
	private String login;
	private String password;
	private String email;
	private String name;
	private String surname;
	private Date birthdate;
	private Byte userType;
	private Byte status;
	private Date tsLogin;
	private Set<Tickets> tickets = new HashSet<Tickets>(0);

	public Users() {
	}

	public Users(String login, String password, String email, String name,
			String surname, Date birthdate, Date tsLogin) {
		this.login = login;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		this.tsLogin = tsLogin;
	}

	public Users(String login, String password, String email, String name,
			String surname, Date birthdate, Byte userType, Byte status) {
		this.login = login;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		this.userType = userType;
		this.status = status;
	}

	public Users(String login, String password, String email, String name,
			String surname, Date birthdate, Byte userType, Byte status,
			Date tsLogin, Set<Tickets> tickets) {
		this.login = login;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		this.userType = userType;
		this.status = status;
		this.tsLogin = tsLogin;
		this.tickets = tickets;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "login", unique = true, nullable = false, length = 32)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "password", unique = true, nullable = false, length = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "email", unique = true, nullable = false, length = 32)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "name", nullable = false, length = 32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "surname", nullable = false, length = 32)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "birthdate", nullable = false, length = 10)
	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	@Column(name = "user_type")
	public Byte getUserType() {
		return this.userType;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
	}

	@Column(name = "status")
	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts_login", nullable = false, length = 19)
	public Date getTsLogin() {
		return this.tsLogin;
	}

	public void setTsLogin(Date tsLogin) {
		this.tsLogin = tsLogin;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "users")
	public Set<Tickets> getTickets() {
		return this.tickets;
	}

	public void setTickets(Set<Tickets> tickets) {
		this.tickets = tickets;
	}
}