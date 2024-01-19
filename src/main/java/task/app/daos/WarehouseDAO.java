package task.app.daos;

import task.app.models.Warehouse;
import task.app.models.WarehouseFilter;

import java.util.List;

public interface WarehouseDAO {

    void createWarehouse(Warehouse warehouse);

    Warehouse findWarehouseById(int id);

    void updateWarehouse(Integer id, Warehouse warehouse);

    void deleteWarehouseById(int id);

    List<Warehouse> getWarehousesByFilter(WarehouseFilter filter);
}
