package task.app.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
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
import task.app.models.WarehouseFilter;
import task.app.models.WarehousesResponse;

@Path("/warehouses")
public class WarehouseController {

    private final WarehouseDAO warehouseDAO;

    @Inject
    public WarehouseController(WarehouseDAO warehouseDAO) {
        this.warehouseDAO = warehouseDAO;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createWarehouse(Warehouse warehouse) {
        warehouseDAO.createWarehouse(warehouse);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findWarehouseByID(@PathParam("id") int id) {
        Warehouse warehouse = warehouseDAO.findWarehouseById(id);
        if (warehouse == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().entity(warehouse).build();
        }
    }

    @GET
    @Path("/filter")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findWarehouses(@BeanParam WarehouseFilter filter) {
        var warehouses = warehouseDAO.getWarehousesByFilter(filter);
        int totalWarehouses = warehouses.size();
        return Response.ok()
                .entity(new WarehousesResponse(warehouses, totalWarehouses))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateWarehouse(@PathParam("id") int id, Warehouse warehouse) {
        try {
            warehouseDAO.updateWarehouse(id, warehouse);
            return Response.status(Response.Status.OK).build();
        } catch (WarehouseNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteWarehouseByID(@PathParam("id") int id) {
        warehouseDAO.deleteWarehouseById(id);
        return Response.ok().build();
    }
}
