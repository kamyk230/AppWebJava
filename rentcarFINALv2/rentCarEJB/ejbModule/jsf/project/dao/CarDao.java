package jsf.project.dao;

import jsf.project.entities.Brand;
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
	
	public Brand findBrandById(int id) {
	    return em.find(Brand.class, id);
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
	
	public List<Car> getAvailableCars() {
	    return em.createQuery("SELECT c FROM Car c WHERE c.isAvailable = true", Car.class)
	             .getResultList();
	}
	
	public List<Car> getUnavailableCars() {
	    return em.createQuery("SELECT c FROM Car c WHERE c.isAvailable = false", Car.class)
	             .getResultList();
	}
	public List<Car> getList(Map<String, Object> searchParams) {
	    List<Car> list = null;

	    // 1. Build query string with parameters
	    String select = "select p ";
	    String from = "from Car p ";
	    String where = "";
	    String orderby = "order by p.brand, p.model asc";

	    // search for brand
	    String brand = (String) searchParams.get("brand");
	    if (brand != null) {
	        if (where.isEmpty()) {
	            where = "where ";
	        } else {
	            where += "and ";
	        }
	        where += "p.brand.brandname like :brand ";
	    }

	    // 2. Create query object
	    Query query = em.createQuery(select + from + where + orderby);

	    // 3. Set configured parameters
	    if (brand != null) {
	        query.setParameter("brand", brand + "%");
	    }

	    // 4. Execute query and retrieve list of Car objects
	    try {
	        list = query.getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	public List<Brand> getAllBrands() {
	    return em.createQuery("SELECT b FROM Brand b", Brand.class).getResultList();
	}

}

 