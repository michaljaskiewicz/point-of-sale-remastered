package pl.javamentor.pointofsaleremastered.pointofsale.domain.model;

import java.util.UUID;

public class ReceiptIdSample {

	public static ReceiptId create() {
		return new ReceiptId(UUID.randomUUID().toString());
	}

}
