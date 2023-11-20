package jsf.project.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Model;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.servlet.http.HttpSession;
import jsf.project.dao.CarDao;
import jsf.project.entities.Car;
import jsf.project.entities.User;

@Named
@RequestScoped
public class CarListBB {
	private static final String PAGE_CAR_EDIT = "carEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String model;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	CarDao carDao;
		
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Car> getFullList(){
		return carDao.getFullList();
	}

	public List<Car> getList(){
		List<Car> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (model != null && model.length() > 0){
			searchParams.put("model", model);
		}
		
		//2. Get list
		list = carDao.getList(searchParams);
		
		return list;
	}

	public String newCar(){
		Car car = new Car();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("car", car);
		
		return PAGE_CAR_EDIT;
	}

	public String editCar(Car car){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("car", car);
		
		return PAGE_CAR_EDIT;
	}

	public String deleteCar(Car car){
		carDao.delete(car);
		return PAGE_STAY_AT_THE_SAME;
	}
}
