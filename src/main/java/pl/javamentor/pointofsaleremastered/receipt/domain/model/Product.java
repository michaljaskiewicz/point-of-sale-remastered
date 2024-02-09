package pl.javamentor.pointofsaleremastered.receipt.domain.model;

import lombok.NonNull;
import lombok.Value;
import pl.javamentor.pointofsaleremastered.money.domain.model.Money;

@Value
public class Product {
	@NonNull ProductBarcode barcode;
	@NonNull ProductName name;
	@NonNull Money price;
}
