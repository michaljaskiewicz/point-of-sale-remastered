package pl.javamentor.pointofsaleremastered.pointofsale.domain.model.command;

import lombok.NonNull;
import lombok.Value;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ReceiptId;

@Value
public class CloseReceipt {
	@NonNull ReceiptId receiptId;
}
