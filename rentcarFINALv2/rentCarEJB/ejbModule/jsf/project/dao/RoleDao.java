package jsf.project.dao;

import jsf.project.entities.Role;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class RoleDao {

    @PersistenceContext
    private EntityManager em;


    public void insert(Role role) {
        em.persist(role);
    }


    public Role update(Role role) {
        return em.merge(role);
    }


    public void delete(int idRole) {
        Role role = get(idRole);
        if (role != null) {
            em.remove(role);
        }
    }


    public Role get(int idRole) {
        return em.find(Role.class, idRole);
    }


    public List<Role> getAll() {
        Query query = em.createNamedQuery("Role.findAll", Role.class);
        return query.getResultList();
    }
}