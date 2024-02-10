package pl.javamentor.pointofsaleremastered.receipt.domain;

import pl.javamentor.pointofsaleremastered.product.domain.model.Product;
import pl.javamentor.pointofsaleremastered.product.domain.model.ProductBarcode;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.exception.ProductNotFoundInCatalogException;

import java.util.Optional;

public interface ProductsCatalog {

	default Product getBy(ProductBarcode barcode) {
		return findBy(barcode)
				.orElseThrow(() -> new ProductNotFoundInCatalogException("Cannot find product by productBarcode = " + barcode));
	}

	Optional<Product> findBy(ProductBarcode barcode);
}
