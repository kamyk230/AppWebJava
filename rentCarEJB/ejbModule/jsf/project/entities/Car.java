package jsf.project.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the car database table.
 * 
 */
@Entity
@NamedQuery(name="Car.findAll", query="SELECT c FROM Car c")
public class Car implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idcar;

	private String model;

	private int price;

	private String productionyear;

	//bi-directional many-to-one association to Brand
	@ManyToOne
	private Brand brand;

	//bi-directional many-to-one association to Rent
	@OneToMany(mappedBy="car")
	private List<Rent> rents;

	public Car() {
	}

	public int getIdcar() {
		return this.idcar;
	}

	public void setIdcar(int idcar) {
		this.idcar = idcar;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getProductionyear() {
		return this.productionyear;
	}

	public void setProductionyear(String productionyear) {
		this.productionyear = productionyear;
	}

	public Brand getBrand() {
		return this.brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public List<Rent> getRents() {
		return this.rents;
	}

	public void setRents(List<Rent> rents) {
		this.rents = rents;
	}

	public Rent addRent(Rent rent) {
		getRents().add(rent);
		rent.setCar(this);

		return rent;
	}

	public Rent removeRent(Rent rent) {
		getRents().remove(rent);
		rent.setCar(null);

		return rent;
	}

}