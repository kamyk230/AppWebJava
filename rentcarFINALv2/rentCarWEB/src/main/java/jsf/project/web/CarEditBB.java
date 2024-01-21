package jsf.project.web;

import java.io.Serializable;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jsf.project.dao.CarDao;
import jsf.project.entities.Brand;
import jsf.project.entities.Car;
import java.util.List;

@Named
@ViewScoped
public class CarEditBB implements Serializable {
    private Car selectedCar;
    private List<Brand> allBrands;
    
    public List<Brand> getAllBrands() {
		return allBrands;
	}
	public void setAllBrands(List<Brand> allBrands) {
		this.allBrands = allBrands;
	}

	@Inject
    private CarDao carDao;

    public Car getSelectedCar() {
        return selectedCar;
    }
    
    @PostConstruct
    public void init() {
        allBrands = carDao.getAllBrands();
        if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedCar") == null) {
            selectedCar = new Car(); 
        } else {
            selectedCar = (Car) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedCar");
        }
    }
    
    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }

    public String saveCar() {
        if (selectedCar.getIdcar() == 0) {
            carDao.insert(selectedCar);
        } else {
            carDao.update(selectedCar);
        }
        return "/public/carList?faces-redirect=true"; 
    }

    public String cancelEdit() {
        return "/public/carList?faces-redirect=true"; 
    }
}