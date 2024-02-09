package pl.javamentor.pointofsaleremastered.receipt.domain;

import pl.javamentor.pointofsaleremastered.receipt.domain.model.ReceiptId;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.exception.ReceiptNotFoundException;

import java.util.Optional;

interface ReceiptRepository {

	void save(Receipt receipt);

	Optional<Receipt> find(ReceiptId receiptId);

	default Receipt get(ReceiptId receiptId) {
		return find(receiptId)
				.orElseThrow(() -> new ReceiptNotFoundException("Cannot find open receipt with receiptId = " + receiptId));
	}

}
