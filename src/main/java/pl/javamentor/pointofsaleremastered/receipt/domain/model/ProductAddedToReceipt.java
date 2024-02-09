package pl.javamentor.pointofsaleremastered.receipt.domain.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class ProductAddedToReceipt {

	@NonNull String productName;
	@NonNull String productPrice;

}
