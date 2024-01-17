package task.app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private Integer id;
        private String name;
        private String addressLine1;
        private String addressLine2;
        private String city;
        private String state;
        private String country;
        private int inventoryQuantity;
}
