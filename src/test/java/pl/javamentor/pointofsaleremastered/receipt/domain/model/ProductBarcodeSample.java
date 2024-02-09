package pl.javamentor.pointofsaleremastered.receipt.domain.model;

import java.util.UUID;

public class ProductBarcodeSample {

	public static ProductBarcode create() {
		return new ProductBarcode(UUID.randomUUID().toString());
	}

}
