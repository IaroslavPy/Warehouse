package task.app.exceptions;

public class WarehouseNotFoundException extends RuntimeException {

    public WarehouseNotFoundException(String message) {
        super(message);
    }
}
