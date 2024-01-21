package jsf.project.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.PrimeFaces;
import org.primefaces.model.LazyDataModel;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Model;
import jakarta.annotation.PostConstruct;
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
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String brand;
	
	private Date desiredStartDate;
	private Date desiredEndDate;


	private String carFilterOption = "available";
	
    @Inject
    private RentDao rentDao;

	public String getCarFilterOption() {
		return carFilterOption;
	}

	public void setCarFilterOption(String carFilterOption) {
		this.carFilterOption = carFilterOption;
	}

	public Date getDesiredStartDate() {
		return desiredStartDate;
	}

	public void setDesiredStartDate(Date desiredStartDate) {
		this.desiredStartDate = desiredStartDate;
	}

	public Date getDesiredEndDate() {
		return desiredEndDate;
	}

	public void setDesiredEndDate(Date desiredEndDate) {
		this.desiredEndDate = desiredEndDate;
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
	
	@Inject
	CarDao carDao;
	
	@Inject
	private CarEditBB carEditBB;
	
	public List<Car> getCarsForCarousel() {
        if ("available".equals(carFilterOption)) {
            return carDao.getAvailableCars();
        } else if ("unavailable".equals(carFilterOption)) {
            return carDao.getUnavailableCars();
        } else {
            return carDao.getFullList(); // Wszystkie samochody
        }
    }

	public void updateCarList() {
        // Logika aktualizacji listy samochodów na podstawie wybranej opcji
    }
	
	public String prepareEdit(Car car) {
	    FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedCar", car);
	    return "/pages/employee/editCar?faces-redirect=true";
	}
	public String prepareAddNewCar() {
		Car newCar = new Car();
	    FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedCar", newCar);
	    return "/pages/employee/editCar?faces-redirect=true";
	}
		

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


	public String deactivateCar(Car car) {
	    car.setisAvailable(false); 
	    carDao.update(car); 
	    return PAGE_STAY_AT_THE_SAME; 
	}

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
	    Flash flash = context.getExternalContext().getFlash();

	    Date today = new Date(); 


	    if (desiredStartDate == null || desiredEndDate == null || 
	            desiredStartDate.before(today) || desiredEndDate.before(today) ||
	            desiredEndDate.before(desiredStartDate)) {
	            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd", "Nieprawidłowy zakres dat."));
	            return PAGE_STAY_AT_THE_SAME;
	        }

	    HttpSession session = (HttpSession) context.getExternalContext().getSession(false);


	    RemoteClient<User> remoteClient = RemoteClient.load(session);
	    
	    User currentUser = null;

	    if (remoteClient != null && remoteClient.getDetails() != null) {
	        currentUser = remoteClient.getDetails(); 
	    }


	    if (currentUser != null) {
	    	List<Rent> existingRents = rentDao.getListForCarAndDates(car.getIdcar(), desiredStartDate, desiredEndDate);
	        boolean isAvailable = existingRents.isEmpty();

	        if (isAvailable) {
	        	Rent newRent = new Rent();
	        	newRent.setCar(car);
	            newRent.setDaterent(desiredStartDate);
	            newRent.setDaterentend(desiredEndDate);
	            newRent.setIsconfirmed("No");  
	            newRent.setUser(currentUser);

	            rentDao.insert(newRent);  
	            flash.setKeepMessages(true);
	            context.addMessage(null, new FacesMessage("Samochód został wstępnie zarezerwowany! Po zatwierdzeniu rezerwacji otrzymasz maila z potwierdzeniem. Dziękujemy za wybór RentCar :)"));
	            try {
	                FacesContext.getCurrentInstance().getExternalContext().redirect("/rentcar/public/carList.xhtml");
	                return null; 
	            } catch (IOException e) {
	                e.printStackTrace(); // 
	            }
	        } else {
	            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd", "Samochód nie jest dostępny w wybranym terminie."));
	            return PAGE_STAY_AT_THE_SAME;  
	        }
	    } else {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd", "Nie można zidentyfikować użytkownika."));
	        return PAGE_STAY_AT_THE_SAME;
	    }
	    return PAGE_STAY_AT_THE_SAME;
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
