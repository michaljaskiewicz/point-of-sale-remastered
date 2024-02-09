package pl.javamentor.pointofsaleremastered.receipt.domain.model.command;

import lombok.NonNull;
import lombok.Value;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.ReceiptId;

@Value
public class CloseReceipt {
	@NonNull ReceiptId receiptId;
}
