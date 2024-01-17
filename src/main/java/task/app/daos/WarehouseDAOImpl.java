package task.app.daos;

import task.app.exceptions.WarehouseNotFoundException;
import task.app.models.Warehouse;
import task.app.utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WarehouseDAOImpl implements WarehouseDOA {
    private final String jdbcURL;
    private final String jdbcUsername;
    private final String jdbcPass;
    private Connection jdbcConn;

    public WarehouseDAOImpl(String jdbcURL, String jdbcUsername, String jdbcPass) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPass = jdbcPass;
    }

    private void connect() throws SQLException {
        if (jdbcConn == null || jdbcConn.isClosed()) {
            try {
                Class.forName(Constants.DB_DRIVER);
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConn = DriverManager.getConnection(
                    jdbcURL, jdbcUsername, jdbcPass);
        }
    }

    private void disconnect() {
        try {
            if (jdbcConn != null && !jdbcConn.isClosed()) {
                jdbcConn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createWarehouse(Warehouse warehouse) {

        String sql = "INSERT INTO warehouse (name, address_line_1," +
                " address_line_2, city, state, country, inventory_quantity)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            connect();

            PreparedStatement statement = jdbcConn.prepareStatement(sql);

            statement.setString(1, warehouse.getName());
            statement.setString(2, warehouse.getAddressLine1());
            statement.setString(3, warehouse.getAddressLine2());
            statement.setString(4, warehouse.getCity());
            statement.setString(5, warehouse.getState());
            statement.setString(6, warehouse.getCountry());
            statement.setInt(7, warehouse.getInventoryQuantity());

            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public Warehouse findWarehouseById(int id) {
        Warehouse warehouse = null;
        String sql = "SELECT * FROM warehouse WHERE id = ?";

        try {
            connect();

            PreparedStatement statement = jdbcConn.prepareStatement(sql);
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

                warehouse = new Warehouse(id, name, addressLine1, addressLine2, city, state, country, inventoryQuantity);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            disconnect();
        }
        return warehouse;
    }

    public void updateWarehouse(Integer id, Warehouse warehouse) throws WarehouseNotFoundException {
        String sql = "UPDATE warehouse SET name = ?, address_line_1 = ?, address_line_2 = ?," +
                " city = ?, state = ?, country = ?, inventory_quantity = ? WHERE id = ?";
        try {
            connect();

            PreparedStatement statement = jdbcConn.prepareStatement(sql);

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
        } finally {
            disconnect();
        }
    }

    public void deleteWarehouseById(int id) {
        String sql = "DELETE FROM warehouse WHERE id = ?";
        try {
            connect();

            PreparedStatement statement = jdbcConn.prepareStatement(sql);
            statement.setInt(1, id);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            disconnect();
        }
    }
}
