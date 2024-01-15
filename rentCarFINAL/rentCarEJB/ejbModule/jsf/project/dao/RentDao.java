package jsf.project.dao;

import jsf.project.entities.Rent;
import java.util.Date;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class RentDao {

    @PersistenceContext
    EntityManager em;

    public void insert(Rent rent) {
        em.persist(rent);
    }

    public Rent update(Rent rent) {
        return em.merge(rent);
    }

    public void delete(int id) {
        Rent rent = get(id);
        if (rent != null) {
            em.remove(rent);
        }
    }

    public Rent get(int id) {
        return em.find(Rent.class, id);
    }

    public List<Rent> getFullList() {
        Query query = em.createQuery("SELECT r FROM Rent r ORDER BY r.daterent DESC");
        return query.getResultList();
    }
    
    public List<Rent> getConfirmedAppointmentsForCarAndDate(int carId, Date desiredDate) {
        Query query = em.createQuery("SELECT r FROM Rent r WHERE r.car.idcar = :carId AND r.daterent = :desiredDate AND r.isconfirmed = 'Yes'");
        query.setParameter("carId", carId);
        query.setParameter("desiredDate", desiredDate);
        return query.getResultList();
    }
    
    public List<Rent> getListForCarAndDate(int carId, Date desiredDate) {
        String jpql = "SELECT r FROM Rent r WHERE r.car.idcar = :carId AND r.daterent = :desiredDate ORDER BY r.daterent";
        Query query = em.createQuery(jpql);
        query.setParameter("carId", carId);
        query.setParameter("desiredDate", desiredDate);
        return query.getResultList();
    }
    
    public List<Rent> getConfirmedAppointmentsForCar(int carId) {
        Query query = em.createQuery("SELECT r FROM Rent r WHERE r.car.idcar = :carId AND r.isconfirmed = 'Yes' ORDER BY r.daterent");
        query.setParameter("carId", carId);
        return query.getResultList();
    }
    
}