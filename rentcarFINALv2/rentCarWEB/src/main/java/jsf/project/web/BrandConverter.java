package jsf.project.web;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jsf.project.dao.CarDao;
import jsf.project.entities.Brand;

@Named
@RequestScoped
public class BrandConverter implements Converter<Brand>{

    @Inject
    private CarDao carDao;

    @Override
    public Brand getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            int id = Integer.parseInt(value);
            return carDao.findBrandById(id);
        } catch (NumberFormatException e) {
            throw new ConverterException("Niepoprawne ID marki: " + value, e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Brand value) {
        if (value == null) {
            return "";
        }

        return String.valueOf(value.getIdBrand());
    }
}