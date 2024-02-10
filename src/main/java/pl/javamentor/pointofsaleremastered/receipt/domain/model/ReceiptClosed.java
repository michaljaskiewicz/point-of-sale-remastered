package pl.javamentor.pointofsaleremastered.receipt.domain.model;

import lombok.NonNull;
import lombok.Value;
import pl.javamentor.pointofsaleremastered.money.domain.model.Money;
import pl.javamentor.pointofsaleremastered.product.domain.model.ProductName;

import java.util.List;

@Value
public class ReceiptClosed {
	@NonNull List<ProductOnReceipt> products;
	@NonNull Money totalSum;

	@Value
	public static class ProductOnReceipt {
		@NonNull ProductName productName;
		@NonNull Money productPrice;
	}
}
