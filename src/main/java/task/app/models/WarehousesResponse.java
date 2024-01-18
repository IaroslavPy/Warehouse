package task.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehousesResponse {

    private List<Warehouse> warehouses;

    private int totalWarehouses;
}
