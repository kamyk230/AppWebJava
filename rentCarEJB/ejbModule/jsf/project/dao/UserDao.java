package jsf.project.dao;

import jsf.project.entities.User;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UserDao {

	@PersistenceContext
	EntityManager em;
	
	public void insert(User user) {
		em.persist(user);
	}

	public User update(User user) {
		return em.merge(user);
	}

	public void delete(User user) {
		em.remove(em.merge(user));
	}

	public User get(Object id) {
		return em.find(User.class, id);
	}
}
 