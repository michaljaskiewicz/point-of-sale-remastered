package pl.javamentor.pointofsaleremastered.receipt.domain.model;

import java.util.UUID;

public class ReceiptIdSample {

	public static ReceiptId create() {
		return new ReceiptId(UUID.randomUUID().toString());
	}

}
