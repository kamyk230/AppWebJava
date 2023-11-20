package jsf.project.dao;

import jsf.project.entities.Car;

import java.util.List;
import java.util.Map;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class CarDao {

	@PersistenceContext
	EntityManager em;
	
	public void insert(Car car) {
		em.persist(car);
	}

	public Car update(Car car) {
		return em.merge(car);
	}

	public void delete(Car car) {
		em.remove(em.merge(car));
	}

	public Car get(Object id) {
		return em.find(Car.class, id);
	}
	public List<Car> getFullList() {
		List<Car> list = null;

		Query query = em.createQuery("select p from Car p");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Car> getList(Map<String, Object> searchParams) {
		List<Car> list = null;

		// 1. Build query string with parameters
		String select = "select p ";
		String from = "from Car p ";
		String where = "";
		String orderby = "order by p.model asc, p.brand";

		// search for surname
		String model = (String) searchParams.get("model");
		if (model != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "p.model like :model ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (model != null) {
			query.setParameter("surname", model+"%");
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of Person objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}

 