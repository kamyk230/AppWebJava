package jsf.project.entities;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 * The primary key class for the rent database table.
 * 
 */
@Embeddable
public class RentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idRent;

	@Column(insertable=false, updatable=false)
	private int users_idUsers;

	@Column(insertable=false, updatable=false)
	private int car_Idcar;

	public RentPK() {
	}
	public int getIdRent() {
		return this.idRent;
	}
	public void setIdRent(int idRent) {
		this.idRent = idRent;
	}
	public int getUsers_idUsers() {
		return this.users_idUsers;
	}
	public void setUsers_idUsers(int users_idUsers) {
		this.users_idUsers = users_idUsers;
	}
	public int getCarIdcar() {
		return this.car_Idcar;
	}
	public void setCarIdcar(int carIdcar) {
		this.car_Idcar = carIdcar;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RentPK)) {
			return false;
		}
		RentPK castOther = (RentPK)other;
		return 
			(this.idRent == castOther.idRent)
			&& (this.users_idUsers == castOther.users_idUsers)
			&& (this.car_Idcar == castOther.car_Idcar);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idRent;
		hash = hash * prime + this.users_idUsers;
		hash = hash * prime + this.car_Idcar;
		
		return hash;
	}
}