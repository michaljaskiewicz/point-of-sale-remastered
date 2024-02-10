package pl.javamentor.pointofsaleremastered.receipt.domain;

import pl.javamentor.pointofsaleremastered.product.domain.model.Product;
import pl.javamentor.pointofsaleremastered.product.domain.model.ProductBarcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemoryProductsCatalog implements ProductsCatalog {

	private final Map<ProductBarcode, Product> products = new HashMap<>();

	Product addProduct(final Product productToAdd) {
		products.put(productToAdd.getBarcode(), productToAdd);
		return productToAdd;
	}

	@Override
	public Optional<Product> findBy(final ProductBarcode barcode) {
		return Optional.ofNullable(products.get(barcode));
	}

}
