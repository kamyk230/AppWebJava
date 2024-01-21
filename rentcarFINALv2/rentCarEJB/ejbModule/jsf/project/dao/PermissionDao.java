package jsf.project.dao;

import jsf.project.entities.Permission;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class PermissionDao {

    @PersistenceContext
    private EntityManager em;


    public void insert(Permission permission) {
        em.persist(permission);
    }


    public Permission update(Permission permission) {
        return em.merge(permission);
    }


    public void delete(int idPermission) {
        Permission permission = get(idPermission);
        if (permission != null) {
            em.remove(permission);
        }
    }


    public Permission get(int idPermission) {
        return em.find(Permission.class, idPermission);
    }


    public List<Permission> getAll() {
        Query query = em.createNamedQuery("Permission.findAll", Permission.class);
        return query.getResultList();
    }
}