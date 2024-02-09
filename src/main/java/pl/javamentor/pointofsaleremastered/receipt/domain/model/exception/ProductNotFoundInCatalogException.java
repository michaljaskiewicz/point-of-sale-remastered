package pl.javamentor.pointofsaleremastered.receipt.domain.model.exception;

import pl.javamentor.pointofsaleremastered.commons.domain.model.exception.DomainObjectNotFoundException;

public class ProductNotFoundInCatalogException extends DomainObjectNotFoundException {
	public ProductNotFoundInCatalogException(final String message) {
		super(message);
	}
}
