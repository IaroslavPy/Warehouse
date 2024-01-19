package task.app.daos;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.experimental.UtilityClass;
import task.app.utils.Constants;

import java.sql.Connection;
import java.sql.SQLException;

@UtilityClass
public class DataSource {
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource dataSource;

    static {
        config.setDriverClassName(Constants.DB_DRIVER);
        config.setJdbcUrl(Constants.DB_URL);
        config.setUsername(Constants.DB_USER);
        config.setPassword(Constants.DB_PASS);
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
