package pl.javamentor.pointofsaleremastered.pointofsale.domain.model.command;

import lombok.Builder;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ReceiptId;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ReceiptIdSample;

import static java.util.Optional.ofNullable;

public class CloseReceiptSample {

	@Builder
	private static CloseReceipt build(final ReceiptId receiptId) {
		return new CloseReceipt(
				ofNullable(receiptId).orElse(ReceiptIdSample.create())
		);
	}

}
