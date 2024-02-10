package pl.javamentor.pointofsaleremastered.product.domain.model;

import pl.javamentor.pointofsaleremastered.product.domain.model.ProductBarcode;

import java.util.UUID;

public class ProductBarcodeSample {

	public static ProductBarcode create() {
		return new ProductBarcode(UUID.randomUUID().toString());
	}

}
