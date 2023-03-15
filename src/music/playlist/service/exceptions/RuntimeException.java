package music.playlist.service.exceptions;

public class RuntimeException extends java.lang.RuntimeException {

    public RuntimeException() {
        super();
    }

    public RuntimeException(String message) {
        super(message);
    }

    public RuntimeException(Throwable cause) {
        super(cause);
    }

    public RuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}