package task.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseEntity {
        private int id;
        private String name;
        private String addressLine1;
        private String addressLine2;
        private String city;
        private String state;
        private String country;
        private int inventoryQuantity;
}
