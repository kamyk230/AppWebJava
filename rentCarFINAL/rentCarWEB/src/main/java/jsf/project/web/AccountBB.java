package jsf.project.web;

import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import jsf.project.dao.UserDao;
import jsf.project.web.LoginBB;
import jsf.project.entities.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.faces.context.FacesContext;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.faces.application.FacesMessage;

import java.io.Serializable;

@Named
@SessionScoped
public class AccountBB implements Serializable {

    private String name;
    private String surname;

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

	@Inject
    UserDao userDao; 

    
	   public String loadUserDetails() {
	        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	        
	        
	        RemoteClient<User> remoteClient = RemoteClient.load(session);
	        
	        
	        if (remoteClient != null && remoteClient.getDetails() != null) {
	            User user = remoteClient.getDetails(); // 
	            this.name = user.getName(); 
	            this.surname = user.getSurname();
	            LoginBB loginBB = new LoginBB();
				loginBB.setisLogged(true);
	        }
	        return "/pages/rent/accountDetails?faces-redirect=true";
	    }
	   public String updateAccountDetails() {
	        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	        
	        RemoteClient<User> remoteClient = RemoteClient.load(session);

	        if (remoteClient != null && remoteClient.getDetails() != null) {
	            User user = remoteClient.getDetails();
	            

	            user.setName(this.name);
	            user.setSurname(this.surname);

	            try {
	                userDao.update(user); 
	                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dane zostały zaktualizowane.", null));
	            } catch (Exception e) {
	                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aktualizacja danych nie powiodła się.", null));
	                return null; 
	            }
	        } else {
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Użytkownik nie jest zalogowany.", null));
	        }

	        return null; 
	    }
}