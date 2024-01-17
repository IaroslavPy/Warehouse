package task.app.daos;

import task.app.models.Warehouse;

import java.sql.SQLException;

public interface WarehouseDOA {

    public void createWarehouse(Warehouse warehouse);

    public Warehouse findWarehouseById(int id);

    public void updateWarehouse(Integer id, Warehouse warehouse);

    public void deleteWarehouseById(int id);
}
