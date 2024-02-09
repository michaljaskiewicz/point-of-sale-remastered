package pl.javamentor.pointofsaleremastered.receipt.domain.model.exception;

import pl.javamentor.pointofsaleremastered.commons.domain.model.exception.DomainObjectNotFoundException;

public class ReceiptNotFoundException extends DomainObjectNotFoundException {
	public ReceiptNotFoundException(final String message) {
		super(message);
	}
}
