package js.web.dto;

import java.io.Serializable;

/**
 * @author Alexander Markov
 */
public class Station implements Comparable<Station>, Serializable {

	private static final long serialVersionUID = -6844224326742911183L;
	private String name;

	@Override
	public int compareTo(Station station) {
		return name.compareToIgnoreCase(station.getName());
	}

	public Station() {
	}

	public Station(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}