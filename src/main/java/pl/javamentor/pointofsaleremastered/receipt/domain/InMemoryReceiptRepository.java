package pl.javamentor.pointofsaleremastered.receipt.domain;

import pl.javamentor.pointofsaleremastered.receipt.domain.model.ReceiptId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemoryReceiptRepository implements ReceiptRepository {

	private final Map<ReceiptId, Receipt> receipts = new HashMap<>();

	@Override
	public void save(final Receipt receipt) {
		receipts.put(receipt.getReceiptId(), receipt);
	}

	@Override
	public Optional<Receipt> find(final ReceiptId receiptId) {
		return Optional.ofNullable(receipts.get(receiptId));
	}
}
