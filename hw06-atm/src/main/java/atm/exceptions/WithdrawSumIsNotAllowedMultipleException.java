package atm.exceptions;

public class WithdrawSumIsNotAllowedMultipleException extends RuntimeException {
    public WithdrawSumIsNotAllowedMultipleException(String message) {
        super(message);
    }

    public WithdrawSumIsNotAllowedMultipleException() {
        super();
    }

}
