package jsf.project.web;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import jsf.project.dao.RentDao;
import jsf.project.entities.Rent;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LazyRentDataModel extends LazyDataModel<Rent> {
    private static final long serialVersionUID = 1L;
    private RentDao rentDao;

    public LazyRentDataModel(RentDao rentDao) {
        this.rentDao = rentDao;
    }

    @Override
    public List<Rent> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        List<Rent> rents = rentDao.getLazyList(first, pageSize);
        if (filterBy != null && !filterBy.isEmpty()) {
            rents = rents.stream()
                    .filter(rent -> true)
                    .collect(Collectors.toList());
        }

        int rowCount = rentDao.count().intValue();
        this.setRowCount(rowCount);

        return rents;
    }

    @Override
    public Rent getRowData(String rowKey) {
        Integer id = Integer.valueOf(rowKey);
        return rentDao.get(id); 
    }

    @Override
    public String getRowKey(Rent rent) {
        return rent != null ? String.valueOf(rent.getIdRent()) : null;
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return rentDao.count().intValue();
    }
}