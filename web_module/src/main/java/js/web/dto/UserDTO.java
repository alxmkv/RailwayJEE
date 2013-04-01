package js.web.dto;

import java.io.Serializable;

/**
 * @author Alexander Markov
 */
public class UserDTO implements Serializable, Comparable<UserDTO> {

	private static final long serialVersionUID = -7966973516051834797L;
	private String login;
	private String email;
	private String name;
	private String surname;
	private String birthdate;
	private String userType;

	public UserDTO(String login, String email, String name, String surname,
			String birthdate, String userType) {
		this.login = login;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		this.userType = userType;
	}

	public UserDTO() {
	}

	@Override
	public int compareTo(UserDTO userDTO) {
		return surname.compareToIgnoreCase(userDTO.getSurname());
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}