package jsf.project.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rent database table.
 * 
 */
@Entity
@NamedQuery(name="Rent.findAll", query="SELECT r FROM Rent r")
public class Rent implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RentPK id;

	@Temporal(TemporalType.DATE)
	private Date daterent;

	private String isconfirmed;

	//bi-directional many-to-one association to Car
	@ManyToOne
	private Car car;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="Users_idUsers")
	private User user;

	public Rent() {
	}

	public RentPK getId() {
		return this.id;
	}

	public void setId(RentPK id) {
		this.id = id;
	}

	public Date getDaterent() {
		return this.daterent;
	}

	public void setDaterent(Date daterent) {
		this.daterent = daterent;
	}

	public String getIsconfirmed() {
		return this.isconfirmed;
	}

	public void setIsconfirmed(String isconfirmed) {
		this.isconfirmed = isconfirmed;
	}

	public Car getCar() {
		return this.car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}