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
	private int idBrand;

	private String brandname;

	//bi-directional many-to-one association to Car
	@OneToMany(mappedBy="brand")
	private List<Car> cars;


	public int getIdBrand() {
		return this.idBrand;
	}

	public void setIdBrand(int idBrand) {
		this.idBrand = idBrand;
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
	@Override
	public boolean equals(Object other) {
	    if (this == other) return true;
	    if (!(other instanceof Brand)) return false;
	    Brand brand = (Brand) other;
	    return getIdBrand() != 0 && getIdBrand() == (brand.getIdBrand());
	}

	@Override
	public int hashCode() {
	    return 31;
	}

}