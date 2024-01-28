package pl.javamentor.pointofsaleremastered.pointofsale.domain;

import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.Product;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ProductBarcode;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.exception.ProductNotFoundInCatalogException;

import java.util.Optional;

public interface ProductsCatalog {

	default Product getBy(ProductBarcode barcode) {
		return findBy(barcode)
				.orElseThrow(() -> new ProductNotFoundInCatalogException("Cannot find product by productBarcode = " + barcode));
	}

	Optional<Product> findBy(ProductBarcode barcode);
}
