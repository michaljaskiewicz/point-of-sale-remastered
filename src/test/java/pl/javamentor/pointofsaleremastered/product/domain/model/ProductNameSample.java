package pl.javamentor.pointofsaleremastered.product.domain.model;

import pl.javamentor.pointofsaleremastered.product.domain.model.ProductName;

import java.util.UUID;

public class ProductNameSample {

	public static ProductName create() {
		return new ProductName(UUID.randomUUID().toString());
	}

}
