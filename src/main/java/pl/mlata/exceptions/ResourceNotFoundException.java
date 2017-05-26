package pl.mlata.exceptions;

/**
 * Created by Mateusz on 24.05.2017.
 */
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;

    public ResourceNotFoundException(String message, String resourceName) {
        super(message);
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
