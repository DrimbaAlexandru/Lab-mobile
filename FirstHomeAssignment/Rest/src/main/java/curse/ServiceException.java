package curse;

/**
 * Created by Xoxii on 28-May-17.
 */
public class ServiceException extends RuntimeException {
    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String message) {
        super(message);
    }
}