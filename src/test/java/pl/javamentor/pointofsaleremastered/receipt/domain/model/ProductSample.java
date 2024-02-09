package pl.javamentor.pointofsaleremastered.receipt.domain.model;

import lombok.Builder;
import pl.javamentor.pointofsaleremastered.money.domain.model.Money;
import pl.javamentor.pointofsaleremastered.money.domain.model.MoneySample;

import static java.util.Optional.ofNullable;

public class ProductSample {

	@Builder
	private static Product build(
			final ProductBarcode barcode,
			final ProductName name,
			final Money price) {
		return new Product(
				ofNullable(barcode).orElse(ProductBarcodeSample.create()),
				ofNullable(name).orElse(ProductNameSample.create()),
				ofNullable(price).orElse(MoneySample.create())
		);
	}

}
