package task.app.daos;

import lombok.NoArgsConstructor;
import task.app.exceptions.WarehouseNotFoundException;
import task.app.models.Warehouse;
import task.app.models.WarehouseFilter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class WarehouseDAOImpl implements WarehouseDAO {

    public void createWarehouse(Warehouse warehouse) {

        String sql = "INSERT INTO warehouse (name, address_line_1," +
                " address_line_2, city, state, country, inventory_quantity)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (
                var connection = DataSource.getConnection();
                var statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, warehouse.getName());
            statement.setString(2, warehouse.getAddressLine1());
            statement.setString(3, warehouse.getAddressLine2());
            statement.setString(4, warehouse.getCity());
            statement.setString(5, warehouse.getState());
            statement.setString(6, warehouse.getCountry());
            statement.setInt(7, warehouse.getInventoryQuantity());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Warehouse findWarehouseById(int id) {
        Warehouse warehouse = null;
        String sql = "SELECT * FROM warehouse WHERE id = ?";

        try (
                var connection = DataSource.getConnection();
                var statement = connection.prepareStatement(sql);
        ) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String addressLine1 = resultSet.getString("address_line_1");
                String addressLine2 = resultSet.getString("address_line_2");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                String country = resultSet.getString("country");
                int inventoryQuantity = resultSet.getInt("inventory_quantity");

                warehouse = new Warehouse(id, name, addressLine1,
                        addressLine2, city, state, country, inventoryQuantity);
            }
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return warehouse;
    }

    public List<Warehouse> getWarehousesByFilter(WarehouseFilter filter) {

        List<Warehouse> warehouses = new ArrayList<>();
        String sql = "SELECT * FROM warehouse WHERE " +
                "(name LIKE ? OR ? IS NULL) AND " +
                "(address_line_1 LIKE ? OR ? IS NULL) AND " +
                "(city LIKE ? OR ? IS NULL) AND " +
                "(state LIKE ? OR ? IS NULL) AND " +
                "(country LIKE ? OR ? IS NULL) AND " +
                "(inventory_quantity LIKE ? OR ? IS NULL) " +
                "ORDER BY " + filter.getSort().name() + " LIMIT ? OFFSET ?";

        try (
                var connection = DataSource.getConnection();
                var statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, filter.getName());
            statement.setString(2, filter.getName());
            statement.setString(3, filter.getAddress());
            statement.setString(4, filter.getAddress());
            statement.setString(5, filter.getCity());
            statement.setString(6, filter.getCity());
            statement.setString(7, filter.getState());
            statement.setString(8, filter.getState());
            statement.setString(9, filter.getCountry());
            statement.setString(10, filter.getCountry());
            statement.setString(11, filter.getInventoryQuantity());
            statement.setString(12, filter.getInventoryQuantity());
            statement.setInt(13, filter.getLimit());
            statement.setInt(14, filter.getOffset());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String nameWarehouse = resultSet.getString("name");
                String addressLine1 = resultSet.getString("address_line_1");
                String addressLine2 = resultSet.getString("address_line_2");
                String cityWarehouse = resultSet.getString("city");
                String stateWarehouse = resultSet.getString("state");
                String countryWarehouse = resultSet.getString("country");
                int inventoryQuantity = resultSet.getInt("inventory_quantity");

                warehouses.add(new Warehouse(id, nameWarehouse, addressLine1, addressLine2,
                        cityWarehouse, stateWarehouse, countryWarehouse, inventoryQuantity));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return warehouses;
    }

    public void updateWarehouse(Integer id, Warehouse warehouse) throws WarehouseNotFoundException {
        String sql = "UPDATE warehouse SET name = ?, address_line_1 = ?, address_line_2 = ?," +
                " city = ?, state = ?, country = ?, inventory_quantity = ? WHERE id = ?";
        try (
                var connection = DataSource.getConnection();
                var statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, warehouse.getName());
            statement.setString(2, warehouse.getAddressLine1());
            statement.setString(3, warehouse.getAddressLine2());
            statement.setString(4, warehouse.getCity());
            statement.setString(5, warehouse.getState());
            statement.setString(6, warehouse.getCountry());
            statement.setInt(7, warehouse.getInventoryQuantity());
            statement.setInt(8, id);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                throw new WarehouseNotFoundException("Warehouse with id " + id + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteWarehouseById(int id) {
        String sql = "DELETE FROM warehouse WHERE id = ?";
        try (
                var connection = DataSource.getConnection();
                var statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
