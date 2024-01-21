package jsf.project.dao;

import jsf.project.entities.Rent;
import java.util.Date;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

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

	public List<Rent> getUserRents(int userId) {
		Query query = em.createQuery("SELECT r FROM Rent r WHERE r.user.id = :userId ORDER BY r.daterent DESC");
		query.setParameter("userId", userId);
		return query.getResultList();
	}

	public List<Rent> getConfirmedAppointmentsForCarAndDates(int carId, Date startDate, Date endDate) {
		String jpql = "SELECT r FROM Rent r WHERE r.car.idcar = :carId AND r.isconfirmed = 'Yes' "
				+ "AND ((r.daterent BETWEEN :startDate AND :endDate) OR (r.daterentend BETWEEN :startDate AND :endDate)"
				+ "OR (:startDate BETWEEN r.daterent AND r.daterentend) OR (:endDate BETWEEN r.daterent AND r.daterentend))";
		Query query = em.createQuery(jpql);
		query.setParameter("carId", carId);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}

	public List<Rent> getListForCarAndDates(int carId, Date startDate, Date endDate) {
	    String jpql = "SELECT r FROM Rent r WHERE r.car.idcar = :carId AND " +
	                  "((r.daterent BETWEEN :startDate AND :endDate) OR " +
	                  "(r.daterentend BETWEEN :startDate AND :endDate) OR " +
	                  "(r.daterent <= :startDate AND r.daterentend >= :endDate)) AND " +
	                  "r.isconfirmed = 'Yes'";
	    Query query = em.createQuery(jpql);
	    query.setParameter("carId", carId);
	    query.setParameter("startDate", startDate);
	    query.setParameter("endDate", endDate);
	    return query.getResultList();
	}

	public List<Rent> getConfirmedAppointmentsForCar(int carId) {
		Query query = em.createQuery(
				"SELECT r FROM Rent r WHERE r.car.idcar = :carId AND r.isconfirmed = 'Yes' ORDER BY r.daterent");
		query.setParameter("carId", carId);
		return query.getResultList();
	}
	
	public List<Rent> getLazyList(int first, int pageSize) {
	    TypedQuery<Rent> query = em.createQuery("SELECT r FROM Rent r ORDER BY r.daterent DESC", Rent.class);
	    query.setFirstResult(first);
	    query.setMaxResults(pageSize);
	    return query.getResultList();
	}

	public Long count() {
	    return em.createQuery("SELECT COUNT(r) FROM Rent r", Long.class).getSingleResult();
	}

    public Rent findById(int id) {
        return em.find(Rent.class, id);
    }

}