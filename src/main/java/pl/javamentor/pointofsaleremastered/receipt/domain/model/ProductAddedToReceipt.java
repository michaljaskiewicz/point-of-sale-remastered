package pl.javamentor.pointofsaleremastered.receipt.domain.model;

import lombok.NonNull;
import lombok.Value;
import pl.javamentor.pointofsaleremastered.money.domain.model.Money;
import pl.javamentor.pointofsaleremastered.product.domain.model.ProductName;

@Value
public class ProductAddedToReceipt {

	@NonNull ProductName productName;
	@NonNull Money productPrice;

}
