package jsf.project.dao;

import jsf.project.entities.User;
import jsf.project.security.SecurityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jsf.project.entities.Permission;
import jsf.project.entities.Role;
import java.util.Date;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Stateless
public class UserDao {

	@PersistenceContext
	EntityManager em;
	
	public void insert(User user) {
		em.persist(user);
	}

	public User update(User user) {
	    if (user == null || user.getIdUsers() == 0) {
	        throw new IllegalArgumentException("User and User ID must not be null or zero");
	    }
	    return em.merge(user);
	}

	public void delete(User user) {
		em.remove(em.merge(user));
	}

	public User get(Object id) {
		return em.find(User.class, id);
	}
	public List<User> getFullList() {
		List<User> list = null;

		Query query = em.createQuery("select p from User p");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public boolean registerUser(String email, String password) {
	    try {

	        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
	        query.setParameter("email", email);
	        List<User> users = query.getResultList();
	        
	        if (!users.isEmpty()) {
	            return false;
	        }


	        User newUser = new User();
	        newUser.setEmail(email);
	        newUser.setPassword(SecurityUtils.hashPassword(password)); 
	        newUser.setWhencreated(new Date()); 

	        em.persist(newUser);


	        Role userRole = em.find(Role.class, 1); 

	        if (userRole == null) {
	            return false;
	        }


	        Permission newPermission = new Permission();
	        newPermission.setUser(newUser);
	        newPermission.setRole(userRole); 
	        newPermission.setWhenset(new Date()); 

	        em.persist(newPermission);

	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public List<User> getList(Map<String, Object> searchParams) {
		List<User> list = null;

		// 1. Build query string with parameters
		String select = "select p ";
		String from = "from User p ";
		String where = "";
		String orderby = "order by p.surname asc, p.name";

		// search for surname
		String surname = (String) searchParams.get("surname");
		if (surname != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "p.surname like :surname ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (surname != null) {
			query.setParameter("surname", surname+"%");
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
public User getUserFromDatabase(String email, String pass) {
		
	User user = null;

    // 1. Build query string with parameters
    String select = "select p ";
    String from = "from User p ";
    String where = "where p.email = :email and p.password = :password ";
    String orderby = "order by p.surname asc, p.name";

    // 2. Create query object
    Query query = em.createQuery(select + from + where + orderby);
    query.setParameter("email", email);
    query.setParameter("password", SecurityUtils.hashPassword(pass)); 

    // 3. Execute query and try to retrieve the user
    try {
        List<User> users = query.getResultList();
        if (!users.isEmpty()) {
            user = users.get(0);
        } else {
            System.out.println("Login failed: Invalid email/password combination.");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return user; 
}




public List<String> getUserRolesFromDatabase(User user) {
    List<String> roles = new ArrayList<>();

    if (user != null && user.getIdUsers() > 0) {
        TypedQuery<Role> query = em.createQuery(
            "SELECT r FROM Permission p JOIN p.role r WHERE p.user.idUsers = :userId", Role.class);
        query.setParameter("userId", user.getIdUsers());

        for (Role role : query.getResultList()) {
            roles.add(role.getRolename()); 
        }
    }

    return roles;
}


}

 