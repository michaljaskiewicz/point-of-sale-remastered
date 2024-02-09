package pl.javamentor.pointofsaleremastered.receipt.domain.model.command;

import lombok.NonNull;
import lombok.Value;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.ProductBarcode;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.ReceiptId;

@Value
public class AddProductToReceipt {
	@NonNull ReceiptId receiptId;
	@NonNull ProductBarcode productBarcode;
}
