package jsf.project.web;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class NavigationBB {

    public String goToHome() {
        return "/public/home?faces-redirect=true";
    }

    public String goToCarList() {
        return "/public/carList?faces-redirect=true";
    }

    public String goToAccountDetails() {
        return "/pages/rent/accountDetails?faces-redirect=true";
    }

    public String goToLogin() {
        return "/login?faces-redirect=true";
    }
    
    public String goToReservationList() {
    	return "/pages/employee/reservationList?faces-redirect=true";
    }
    
    public String goToUserList() {
    	return "/pages/admin/userList?faces-redirect=true";
    }

}