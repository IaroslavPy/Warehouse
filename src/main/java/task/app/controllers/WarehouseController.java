package task.app.controllers;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import task.app.daos.WarehouseDAO;
import task.app.exceptions.WarehouseNotFoundException;
import task.app.models.Warehouse;
import task.app.utils.Constants;

import java.sql.SQLException;

@Path("/warehouses")
public class WarehouseController {

    private final WarehouseDAO warehouseDAO = new WarehouseDAO(Constants.DB_URL,
            Constants.DB_USER, Constants.DB_PASS);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createWarehouse(Warehouse warehouse) {
        try {
            warehouseDAO.createWarehouse(warehouse);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findWarehouseByID(@PathParam("id") int id) {
        try {
            Warehouse warehouse = warehouseDAO.findWarehouseById(id);
            if (warehouse != null) {
                return Response.ok().entity(warehouse).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWarehouse(Warehouse warehouse) {
        try {
            warehouseDAO.updateWarehouse(warehouse);
            return Response.status(Response.Status.OK).build();
        } catch (WarehouseNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteWarehouseByID(@PathParam("id") int id) {
        try {
            warehouseDAO.deleteWarehouseById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Response.ok().build();
    }
}
