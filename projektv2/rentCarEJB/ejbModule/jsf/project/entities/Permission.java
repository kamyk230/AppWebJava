package jsf.project.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;


/**
 * The persistent class for the permission database table.
 * 
 */
@Entity
@NamedQuery(name="Permission.findAll", query="SELECT p FROM Permission p")
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPermission;

	@Temporal(TemporalType.DATE)
	private Date whenrevoked;

	@Temporal(TemporalType.DATE)
	private Date whenset;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="Users_idUsers")
	private User user;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="Role_idRole")
	private Role role;

	public Permission() {
	}

	public int getIdPermission() {
		return this.idPermission;
	}

	public void setIdPermission(int idPermission) {
		this.idPermission = idPermission;
	}

	public Date getWhenrevoked() {
		return this.whenrevoked;
	}

	public void setWhenrevoked(Date whenrevoked) {
		this.whenrevoked = whenrevoked;
	}

	public Date getWhenset() {
		return this.whenset;
	}

	public void setWhenset(Date whenset) {
		this.whenset = whenset;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}