package austral.ingsis.jjuserservice.exception;

public class FollowerNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 469276813116690371L;

    public FollowerNotFoundException() {
        super();
    }

    public FollowerNotFoundException(String message) {
        super(message);
    }

    public FollowerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FollowerNotFoundException(Throwable cause) {
        super(cause);
    }

    protected FollowerNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
