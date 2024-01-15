package jsf.project.web;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import jsf.project.dao.UserDao;
import jsf.project.entities.User;

@Named
@SessionScoped
public class LoginBB implements Serializable{
	private static final String PAGE_MAIN = "/public/home?faces-redirect=true";
	private static final String PAGE_LOGIN = "/pages/login?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String email;
	private String pass;
	private boolean isLogged = false;
	private boolean isEmployee = false;
	private boolean isAdmin = false;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public boolean getisLogged() {
		return isLogged;
	}

	public void setisLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}
	
	public boolean getisEmployee() {
		return isEmployee;
	}

	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}
	

	@Inject
	UserDao userDAO;
	public String goToLogin() {
		return PAGE_LOGIN;
	}


	public String doLogin() {
	    FacesContext ctx = FacesContext.getCurrentInstance();

	    User user = userDAO.getUserFromDatabase(email, pass);

	    if (user == null) {
	        ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Niepoprawny login lub hasło", null));
	        return PAGE_STAY_AT_THE_SAME;
	    }

	    RemoteClient<User> client = new RemoteClient<User>(); 
	    client.setDetails(user);

	    List<String> roles = userDAO.getUserRolesFromDatabase(user); 

	    if (roles != null) { 
	        for (String role : roles) {
	            client.getRoles().add(role);
	            if ("employee".equals(role)) { 
	                isEmployee = true;
	            } else if ("admin".equals(role)) {
	            	setAdmin(true);
	            }
	        }
	    }

	    HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
	    client.store(request);

	    this.isLogged = true;
	    return PAGE_MAIN;
	}
	
	
	public String doLogout(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		session.invalidate();
		this.isLogged = false;
		return PAGE_LOGIN;
	}

	public boolean getisAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	





}
