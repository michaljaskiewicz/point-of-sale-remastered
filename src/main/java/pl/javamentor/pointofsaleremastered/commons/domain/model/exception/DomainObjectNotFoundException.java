package pl.javamentor.pointofsaleremastered.commons.domain.model.exception;

public class DomainObjectNotFoundException extends RuntimeException {

	public DomainObjectNotFoundException(final String message) {
		super(message);
	}
}
