package pl.javamentor.pointofsaleremastered.receipt.domain.model;

import java.util.UUID;

public class ProductNameSample {

	public static ProductName create() {
		return new ProductName(UUID.randomUUID().toString());
	}

}
