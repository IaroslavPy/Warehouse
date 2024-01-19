package task.app;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import task.app.controllers.WarehouseController;
import task.app.daos.WarehouseDAO;
import task.app.daos.WarehouseDAOImpl;

@ApplicationPath("/api")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        register(WarehouseController.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(WarehouseDAOImpl.class).to(WarehouseDAO.class);
            }
        });
    }
}
