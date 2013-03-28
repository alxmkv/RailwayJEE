package js.web.cdi;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * @author Alexander Markov
 */
@RequestScoped
@Named
public class LogoutBean implements Serializable {

	private static final long serialVersionUID = -7251416790365324040L;

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.remove("login");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.remove("type");
		return "index";
	}

	public Boolean isAdmin() {
		return ((Integer) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("type") == 1);
	}
}