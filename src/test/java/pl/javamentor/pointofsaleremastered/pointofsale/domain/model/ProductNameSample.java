package pl.javamentor.pointofsaleremastered.pointofsale.domain.model;

import java.util.UUID;

public class ProductNameSample {

	public static ProductName create() {
		return new ProductName(UUID.randomUUID().toString());
	}

}
