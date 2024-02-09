package pl.javamentor.pointofsaleremastered.receipt.domain;

import lombok.RequiredArgsConstructor;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.Product;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.ProductAddedToReceipt;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.ReceiptClosed;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.ReceiptId;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.command.AddProductToReceipt;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.command.CloseReceipt;

@RequiredArgsConstructor
public class ReceiptFacade {

	private final ProductsCatalog productsCatalog;
	private final ReceiptRepository receiptRepository;

	public ReceiptId openReceipt() {
		final Receipt receipt = Receipt.create();
		receiptRepository.save(receipt);
		return receipt.getReceiptId();
	}

	public ProductAddedToReceipt addProductToReceipt(final AddProductToReceipt command) {
		final Receipt receipt = receiptRepository.get(command.getReceiptId());
		final Product product = productsCatalog.getBy(command.getProductBarcode());
		final ProductAddedToReceipt productAddedToReceipt = receipt.addProduct(product);
		receiptRepository.save(receipt);
		return productAddedToReceipt;
	}

	public ReceiptClosed closeReceipt(final CloseReceipt command) {
		final Receipt receipt = receiptRepository.get(command.getReceiptId());
		final ReceiptClosed receiptClosed = receipt.close();
		receiptRepository.save(receipt);
		return receiptClosed;
	}

}
