package task.app.models;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseFilter {

    @QueryParam("name")
    private String name;

    @QueryParam("address")
    private String address;

    @QueryParam("city")
    private String city;

    @QueryParam("state")
    private String state;

    @QueryParam("country")
    private String country;

    @QueryParam("inventoryQuantity")
    private String inventoryQuantity;

    @DefaultValue(value = "20")
    @QueryParam("limit")
    private int limit;

    @DefaultValue(value = "0")
    @QueryParam("offset")
    private Integer offset;

    @DefaultValue(value = "NAME")
    @QueryParam("sort")
    private WarehouseSort sort;
}
