package pl.javamentor.pointofsaleremastered.pointofsale.domain.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class ProductAddedToReceipt {

	@NonNull String productName;
	@NonNull String productPrice;

}
