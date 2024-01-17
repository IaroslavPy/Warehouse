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
import task.app.daos.WarehouseDAOImpl;
import task.app.exceptions.WarehouseNotFoundException;
import task.app.models.Warehouse;
import task.app.utils.Constants;

@Path("/warehouses")
public class WarehouseController {

    private final WarehouseDAOImpl warehouseDAOImpl = new WarehouseDAOImpl(Constants.DB_URL,
            Constants.DB_USER, Constants.DB_PASS);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createWarehouse(Warehouse warehouse) {
        warehouseDAOImpl.createWarehouse(warehouse);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findWarehouseByID(@PathParam("id") int id) {
        Warehouse warehouse = warehouseDAOImpl.findWarehouseById(id);
        if (warehouse == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().entity(warehouse).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWarehouse(@PathParam("id") int id, Warehouse warehouse) {
        try {
            warehouseDAOImpl.updateWarehouse(id, warehouse);
            return Response.status(Response.Status.OK).build();
        } catch (WarehouseNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteWarehouseByID(@PathParam("id") int id) {
        warehouseDAOImpl.deleteWarehouseById(id);
        return Response.ok().build();
    }
}
