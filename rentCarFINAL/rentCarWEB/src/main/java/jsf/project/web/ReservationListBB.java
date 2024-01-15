package jsf.project.web;

import jsf.project.dao.RentDao;
import jsf.project.entities.Rent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ReservationListBB {

    @Inject
    private RentDao rentDao;

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
            List<Rent> confirmedAppointments = rentDao.getConfirmedAppointmentsForCarAndDate(reservation.getCar().getIdcar(), reservation.getDaterent());
            
            if (confirmedAppointments.isEmpty()) {
                reservation.setIsconfirmed("Yes");
                rentDao.update(reservation);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sukces", "Rezerwacja została potwierdzona."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Konflikt", "Istnieje już potwierdzona rezerwacja na ten samochód w wybranym dniu."));
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