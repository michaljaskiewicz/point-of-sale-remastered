package pl.javamentor.pointofsaleremastered.pointofsale.domain.model.exception;

public class ReceiptAlreadyClosedException extends RuntimeException {

	public ReceiptAlreadyClosedException(final String message) {
		super(message);
	}
}
