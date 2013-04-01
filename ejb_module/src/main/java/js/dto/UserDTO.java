package js.dto;

import java.util.Date;

/**
 * @author Alexander Markov
 */
public class UserDTO {

	private String name;
	private String surname;
	private Date birthdate;
	private String email;
	private Byte userType;
	private Byte status;

	public UserDTO(String name, String surname, Date birthdate, String email,
			Byte userType, Byte status) {
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		this.email = email;
		this.userType = userType;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public String getEmail() {
		return email;
	}

	public Byte getUserType() {
		return userType;
	}

	public Byte getStatus() {
		return status;
	}
}