package pl.javamentor.pointofsaleremastered.receipt.domain.model.command;

import lombok.Builder;
import pl.javamentor.pointofsaleremastered.product.domain.model.ProductBarcode;
import pl.javamentor.pointofsaleremastered.product.domain.model.ProductBarcodeSample;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.ReceiptId;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.ReceiptIdSample;

import static java.util.Optional.ofNullable;

public class AddProductToReceiptSample {

	@Builder
	private static AddProductToReceipt build(
			final ReceiptId receiptId,
			final ProductBarcode productBarcode) {
		return new AddProductToReceipt(
				ofNullable(receiptId).orElse(ReceiptIdSample.create()),
				ofNullable(productBarcode).orElse(ProductBarcodeSample.create())
		);
	}

}
