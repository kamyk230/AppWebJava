package jsf.project.dao;

import jsf.project.entities.Brand;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class BrandDao {

    @PersistenceContext
    private EntityManager em;


    public void insert(Brand brand) {
        em.persist(brand);
    }


    public Brand update(Brand brand) {
        return em.merge(brand);
    }


    public void delete(int idBrand) {
        Brand brand = get(idBrand);
        if (brand != null) {
            em.remove(brand);
        }
    }


    public Brand get(int idBrand) {
        return em.find(Brand.class, idBrand);
    }


    public List<Brand> getAll() {
        Query query = em.createNamedQuery("Brand.findAll", Brand.class);
        return query.getResultList();
    }
}