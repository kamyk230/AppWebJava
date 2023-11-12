package jsf.project.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the brand database table.
 * 
 */
@Entity
@NamedQuery(name="Brand.findAll", query="SELECT b FROM Brand b")
public class Brand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idbrand;

	private String brandname;

	//bi-directional many-to-one association to Car
	@OneToMany(mappedBy="brand")
	private List<Car> cars;

	public Brand() {
	}

	public int getIdbrand() {
		return this.idbrand;
	}

	public void setIdbrand(int idbrand) {
		this.idbrand = idbrand;
	}

	public String getBrandname() {
		return this.brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public List<Car> getCars() {
		return this.cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public Car addCar(Car car) {
		getCars().add(car);
		car.setBrand(this);

		return car;
	}

	public Car removeCar(Car car) {
		getCars().remove(car);
		car.setBrand(null);

		return car;
	}

}