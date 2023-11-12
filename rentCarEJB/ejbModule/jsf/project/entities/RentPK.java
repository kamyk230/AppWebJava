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

	private int idrent;

	@Column(name="users_iduser", insertable=false, updatable=false)
	private int usersIduser;

	@Column(name="car_idcar", insertable=false, updatable=false)
	private int carIdcar;

	public RentPK() {
	}
	public int getIdrent() {
		return this.idrent;
	}
	public void setIdrent(int idrent) {
		this.idrent = idrent;
	}
	public int getUsersIduser() {
		return this.usersIduser;
	}
	public void setUsersIduser(int usersIduser) {
		this.usersIduser = usersIduser;
	}
	public int getCarIdcar() {
		return this.carIdcar;
	}
	public void setCarIdcar(int carIdcar) {
		this.carIdcar = carIdcar;
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
			(this.idrent == castOther.idrent)
			&& (this.usersIduser == castOther.usersIduser)
			&& (this.carIdcar == castOther.carIdcar);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idrent;
		hash = hash * prime + this.usersIduser;
		hash = hash * prime + this.carIdcar;
		
		return hash;
	}
}