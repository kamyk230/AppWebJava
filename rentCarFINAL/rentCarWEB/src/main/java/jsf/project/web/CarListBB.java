package jsf.project.web;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Model;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.servlet.http.HttpSession;
import jsf.project.dao.CarDao;
import jsf.project.dao.RentDao;
import jsf.project.dao.UserDao;
import jsf.project.entities.Car;
import jsf.project.entities.Rent;
import jsf.project.entities.User;

@Named
@SessionScoped
public class CarListBB implements Serializable{
	private static final String PAGE_CAR_EDIT = "carEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String brand;
	
	private Date desiredDate;

	// Getters and Setters
	public Date getDesiredDate() {
	    return desiredDate;
	}

	public void setDesiredDate(Date desiredDate) {
	    this.desiredDate = desiredDate;
	}

	public String getBrand() {
	    return brand;
	}

	public void setBrand(String brand) {
	    this.brand = brand;
	}
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	CarDao carDao;
		

	public List<Car> getFullList(){
		return carDao.getFullList();
	}

	public List<Car> getList(){
	    List<Car> list = null;

	    Map<String,Object> searchParams = new HashMap<String, Object>();
	    
	    if (brand != null && brand.length() > 0){
	        searchParams.put("brand", brand);
	    }
	    
	    list = carDao.getList(searchParams);
	    
	    return list;
	}

	public String newCar(){
		Car car = new Car();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("car", car);
		
		return PAGE_CAR_EDIT;
	}

	public String editCar(Car car){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("car", car);
		
		return PAGE_CAR_EDIT;
	}

	public String deleteCar(Car car){
		carDao.delete(car);
		return PAGE_STAY_AT_THE_SAME;
	}
	@Inject
	RentDao rentDao;
	private int selectedCarId;

	public int getSelectedCarId() {
	    return selectedCarId;
	}

	public void setSelectedCarId(int selectedCarId) {
	    this.selectedCarId = selectedCarId;
	}

	public String prepareReservation(int carId){
	    this.selectedCarId = carId;
	    return "/pages/rent/carReservation?faces-redirect=true";  
	}

	public String reserveCar() {
	    int selectedCarId = this.selectedCarId;
	    if (selectedCarId == 0) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd", "Nie wybrano samochodu."));
	        return null;
	    }

	    Car car = carDao.get(selectedCarId);
	    FacesContext context = FacesContext.getCurrentInstance();


	    Date desiredDate = this.desiredDate;
	    Date today = new Date(); 


	    if (desiredDate.before(today) || isSameDay(desiredDate, today)) {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd", "Data rezerwacji musi być co najmniej jeden dzień do przodu."));
	        return PAGE_STAY_AT_THE_SAME;
	    }


	    HttpSession session = (HttpSession) context.getExternalContext().getSession(false);


	    RemoteClient<User> remoteClient = RemoteClient.load(session);
	    
	    User currentUser = null;

	    if (remoteClient != null && remoteClient.getDetails() != null) {
	        currentUser = remoteClient.getDetails(); 
	    }


	    if (currentUser != null) {
	        List<Rent> existingRents = rentDao.getListForCarAndDate(car.getIdcar(), desiredDate);
	        boolean isAvailable = existingRents.stream().noneMatch(rent -> rent.getIsconfirmed().equals("Yes"));

	        if (isAvailable) {
	            Rent newRent = new Rent();
	            newRent.setCar(car);
	            newRent.setDaterent(desiredDate);
	            newRent.setIsconfirmed("No");  

	            newRent.setUser(currentUser);

	            rentDao.insert(newRent);  

	            context.addMessage(null, new FacesMessage("Samochód zarezerwowany!"));
	            return "/public/carList?faces-redirect=true";  
	        } else {
	            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd", "Samochód nie jest dostępny w wybranym terminie."));
	            return PAGE_STAY_AT_THE_SAME;  
	        }
	    } else {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd", "Nie można zidentyfikować użytkownika."));
	        return PAGE_STAY_AT_THE_SAME;
	    }
	}
	 public List<Rent> getConfirmedAppointments() {
	        return rentDao.getConfirmedAppointmentsForCar(selectedCarId);
	    }
	 public static boolean isSameDay(Date date1, Date date2) {
		    Calendar cal1 = Calendar.getInstance();
		    Calendar cal2 = Calendar.getInstance();
		    cal1.setTime(date1);
		    cal2.setTime(date2);
		    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
		           cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
		}
}
