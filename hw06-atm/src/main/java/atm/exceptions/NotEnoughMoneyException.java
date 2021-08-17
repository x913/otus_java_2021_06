package atm.exceptions;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(String message) {
        super(message);
    }

    public NotEnoughMoneyException() {
        super();
    }
}
