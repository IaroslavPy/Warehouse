package task.app.daos;

import task.app.models.Warehouse;

import java.util.List;

public interface WarehouseDOA {

    public void createWarehouse(Warehouse warehouse);

    public Warehouse findWarehouseById(int id);

    public void updateWarehouse(Integer id, Warehouse warehouse);

    public void deleteWarehouseById(int id);

    public List<Warehouse> getWarehousesByFilter(
            String name, String address, String city,
            String state, String country, String quantity,
            int limit, int offset, String sort);
}
