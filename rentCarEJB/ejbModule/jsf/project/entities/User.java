package jsf.project.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUsers;

	private String email;

	private String name;

	private String password;

	private String surname;

	@Temporal(TemporalType.DATE)
	private Date whencreated;

	@Temporal(TemporalType.DATE)
	private Date whenmodified;

	private String whocreated;

	private String whomodified;

	//bi-directional many-to-one association to Rent
	@OneToMany(mappedBy="user")
	private List<Rent> rents;

	//bi-directional many-to-one association to Permission
	@OneToMany(mappedBy="user")
	private List<Permission> permissions;

	public User() {
	}

	public int getIdUsers() {
		return this.idUsers;
	}

	public void setIdUsers(int idUsers) {
		this.idUsers = idUsers;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getWhencreated() {
		return this.whencreated;
	}

	public void setWhencreated(Date whencreated) {
		this.whencreated = whencreated;
	}

	public Date getWhenmodified() {
		return this.whenmodified;
	}

	public void setWhenmodified(Date whenmodified) {
		this.whenmodified = whenmodified;
	}

	public String getWhocreated() {
		return this.whocreated;
	}

	public void setWhocreated(String whocreated) {
		this.whocreated = whocreated;
	}

	public String getWhomodified() {
		return this.whomodified;
	}

	public void setWhomodified(String whomodified) {
		this.whomodified = whomodified;
	}

	public List<Rent> getRents() {
		return this.rents;
	}

	public void setRents(List<Rent> rents) {
		this.rents = rents;
	}

	public Rent addRent(Rent rent) {
		getRents().add(rent);
		rent.setUser(this);

		return rent;
	}

	public Rent removeRent(Rent rent) {
		getRents().remove(rent);
		rent.setUser(null);

		return rent;
	}

	public List<Permission> getPermissions() {
		return this.permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public Permission addPermission(Permission permission) {
		getPermissions().add(permission);
		permission.setUser(this);

		return permission;
	}

	public Permission removePermission(Permission permission) {
		getPermissions().remove(permission);
		permission.setUser(null);

		return permission;
	}

}