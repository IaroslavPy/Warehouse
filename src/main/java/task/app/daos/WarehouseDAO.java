package task.app.daos;

import task.app.exceptions.WarehouseNotFoundException;
import task.app.models.Warehouse;
import task.app.utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WarehouseDAO {

    private final String jdbcURL;
    private final String jdbcUsername;
    private final String jdbcPass;
    private Connection jdbcConn;

    public WarehouseDAO(String jdbcURL, String jdbcUsername, String jdbcPass) {
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

    private void disconnect() throws SQLException {
        if (jdbcConn != null && !jdbcConn.isClosed()) {
            jdbcConn.close();
        }
    }

    public void createWarehouse(Warehouse warehouse) throws SQLException {
        String sql = "INSERT INTO warehouse (id, name, address_line_1," +
                " address_line_2, city, state, country, inventory_quantity)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        connect();

        PreparedStatement statement = jdbcConn.prepareStatement(sql);

        statement.setInt(1, warehouse.getId());
        statement.setString(2, warehouse.getName());
        statement.setString(3, warehouse.getAddressLine1());
        statement.setString(4, warehouse.getAddressLine2());
        statement.setString(5, warehouse.getCity());
        statement.setString(6, warehouse.getState());
        statement.setString(7, warehouse.getCountry());
        statement.setInt(8, warehouse.getInventoryQuantity());

        statement.executeUpdate();
        statement.close();
        disconnect();
    }

    public Warehouse findWarehouseById(int id) throws SQLException {
        Warehouse warehouse = null;
        String sql = "SELECT * FROM warehouse WHERE id = ?";

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
        disconnect();

        return warehouse;
    }

    public void updateWarehouse(Warehouse warehouse) throws SQLException, WarehouseNotFoundException {
        String sql = "UPDATE warehouse SET name = ?, address_line_1 = ?, address_line_2 = ?," +
                " city = ?, state = ?, country = ?, inventory_quantity = ? WHERE id = ?";

        connect();

        try (PreparedStatement statement = jdbcConn.prepareStatement(sql)) {

            statement.setString(1, warehouse.getName());
            statement.setString(2, warehouse.getAddressLine1());
            statement.setString(3, warehouse.getAddressLine2());
            statement.setString(4, warehouse.getCity());
            statement.setString(5, warehouse.getState());
            statement.setString(6, warehouse.getCountry());
            statement.setInt(7, warehouse.getInventoryQuantity());
            statement.setInt(8, warehouse.getId());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                throw new WarehouseNotFoundException("Warehouse with id " + warehouse.getId() + " not found");
            }
        } finally {
            disconnect();
        }
    }

    public void deleteWarehouseById(int id) throws SQLException {

        String sql = "DELETE FROM warehouse WHERE id = ?";

        connect();

        PreparedStatement statement = jdbcConn.prepareStatement(sql);
        statement.setInt(1, id);

        statement.executeUpdate();
        statement.close();
        disconnect();
    }
}
