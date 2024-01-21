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

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int idRent; 

    @Temporal(TemporalType.DATE)
    private Date daterent;
    
    @Temporal(TemporalType.DATE)
    private Date daterentend;

    public Date getDaterentend() {
		return daterentend;
	}

	public void setDaterentend(Date daterentend) {
		this.daterentend = daterentend;
	}

	private String isconfirmed;

    //bi-directional many-to-one association to Car
    @ManyToOne
    private Car car;

    //bi-directional many-to-one association to User
    @ManyToOne
    @JoinColumn(name="Users_idUsers")
    private User user;

    
    public int getIdRent() {
        return this.idRent;
    }

    public void setIdRent(int idRent) {
        this.idRent = idRent;
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
	
	public int getUserId() {
	    return this.user != null ? this.user.getIdUsers() : null;
	}

}