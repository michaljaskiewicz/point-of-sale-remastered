package pl.javamentor.pointofsaleremastered.receipt.domain.model;

import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
public class ReceiptClosed {
	@NonNull List<ProductOnReceipt> products;
	@NonNull String totalSum;

	@Value
	public static class ProductOnReceipt {
		@NonNull String productName;
		@NonNull String productPrice;
	}
}
