package jsf.project.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;


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
	private int idusers;

	private String email;

	private String name;

	private String password;

	private String surname;

	@Temporal(TemporalType.DATE)
	private Date whencreated;

	@Temporal(TemporalType.DATE)
	private Date whenmodified;

	private int whocreated;

	private int whomodified;

	public User() {
	}

	public int getIdusers() {
		return this.idusers;
	}

	public void setIdusers(int idusers) {
		this.idusers = idusers;
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

	public int getWhocreated() {
		return this.whocreated;
	}

	public void setWhocreated(int whocreated) {
		this.whocreated = whocreated;
	}

	public int getWhomodified() {
		return this.whomodified;
	}

	public void setWhomodified(int whomodified) {
		this.whomodified = whomodified;
	}

}