package jsf.project.web;

import jsf.project.dao.RentDao;
import jsf.project.entities.Rent;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

import org.primefaces.model.LazyDataModel;

@Named
@RequestScoped
public class ReservationListBB {

    @Inject
    private RentDao rentDao;
    
    private LazyDataModel<Rent> lazyModel;
    @PostConstruct
    public void init() {
        lazyModel = new LazyRentDataModel(rentDao);
    }

    public LazyDataModel<Rent> getLazyModel() {
        return lazyModel;
    }
    
    private List<Rent> reservations;

    public List<Rent> getReservations() {
        if (reservations == null) {
            reservations = rentDao.getFullList();
        }
        return reservations;
    }

    public String confirmReservation(int rentId) {
        Rent reservation = rentDao.get(rentId);
        if (reservation != null && "No".equals(reservation.getIsconfirmed())) {
            List<Rent> confirmedAppointments = rentDao.getConfirmedAppointmentsForCarAndDates(
                reservation.getCar().getIdcar(),
                reservation.getDaterent(),
                reservation.getDaterentend());

            if (confirmedAppointments.isEmpty()) {
                reservation.setIsconfirmed("Yes");
                rentDao.update(reservation);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sukces", "Rezerwacja została potwierdzona."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Konflikt", "Istnieje już potwierdzona rezerwacja na ten samochód w wybranym terminie."));
                return null;
            }
        }
        reservations = rentDao.getFullList(); 
        return null; 
    }

    public String cancelReservation(int rentId) {
        Rent reservation = rentDao.get(rentId);
        if (reservation != null && "Yes".equals(reservation.getIsconfirmed())) {
            reservation.setIsconfirmed("No");
            rentDao.update(reservation);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Anulowano", "Rezerwacja została anulowana."));
            reservations = rentDao.getFullList();
        }
        return null; 
    }
}